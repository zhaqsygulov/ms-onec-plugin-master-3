{{/* vim: set filetype=mustache: */}}
{{/*
Expand the name of the chart.
*/}}
{{- define "chart.name" -}}
{{- default .Chart.Name .Values.nameOverride | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{/*
Create a default fully qualified app name.
We truncate at 63 chars because some Kubernetes name fields are limited to this (by the DNS naming spec).
If release name contains chart name it will be used as a full name.
*/}}
{{- define "chart.fullname" -}}
{{- if .Values.fullnameOverride -}}
{{- .Values.fullnameOverride | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- $name := default .Chart.Name .Values.nameOverride -}}
{{- if contains $name .Release.Name -}}
{{- .Release.Name | trunc 63 | trimSuffix "-" -}}
{{- else -}}
{{- printf "%s-%s" .Release.Name $name | trunc 63 | trimSuffix "-" -}}
{{- end -}}
{{- end -}}
{{- end -}}

{{/*
Create chart name and version as used by the chart label.
*/}}
{{- define "chart.chart" -}}
{{- printf "%s-%s" .Chart.Name .Chart.Version | replace "+" "_" | trunc 63 | trimSuffix "-" -}}
{{- end -}}

{{- define "serviceAccount.name" -}}
{{- if .Values.serviceAccount.create -}}
{{- .Values.serviceAccount.name | default "default" -}}
{{- else -}}
{{- "default" -}}
{{- end -}}
{{- end -}}

{{ define "probeSpec" }}
            initialDelaySeconds: {{ .initialDelaySeconds }}
            periodSeconds: {{ .periodSeconds }}
            timeoutSeconds: {{ .timeoutSeconds }}
            successThreshold: {{ .successThreshold }}
            failureThreshold: {{ .failureThreshold }}
            {{ .type }}:
              {{- if .port }}
              port: {{ .port }}
              {{- end }}
              {{- if .path }}
              path: {{ .path }}
              {{- end }}
              {{- if .httpHeaderName }}
              httpHeaders:
                - name: {{ .httpHeaderName }}
                  value: {{ .httpHeaderValue }}
              {{- end }}
              {{- if .command }}
              command:
                {{- range .command }}
                - {{ . }}
                {{- end }}
              {{- end }}
{{- end -}}

{{ define "podSpec" }}
    spec:
      {{- with .Values.imagePullSecrets }}
      imagePullSecrets:
      - name: {{ toYaml  . }}
      {{- end }}
      {{- with .Values.dnsConfig }}
      dnsConfig: {{ toYaml . | nindent  8 }}
      {{- end }}
      {{- with .Values.hostAliases }}
      hostAliases: {{ toYaml . | nindent  8 }}
      {{- end }}
      {{- with .Values.terminationGracePeriodSeconds }}
      terminationGracePeriodSeconds: {{ . }}
      {{- end }}
      {{- with .Values.nodeSelector }}
      nodeSelector: {{ toYaml . | nindent 8 }}
      {{- end }}
      {{- with .Values.affinity }}
      affinity: {{ toYaml . | nindent 8 }}
      {{- end }}
      {{- with .Values.tolerations }}
      tolerations: {{ toYaml . | nindent 8 }}
      {{- end }}
        {{/*
        spec for container
        */}}
      containers:
        {{- with .Values }}
        - name: {{ .containerName }}
          {{- $tag :=  .image.tag }}
          {{- $type := printf "%T" $tag }}
          image: "{{ .image.repository }}/{{.image.branch}}:{{if eq $type "float64"}}{{ printf "%.0f" $tag }}{{ else }}{{ $tag }}{{ end }}"
          imagePullPolicy: {{ .image.pullPolicy }}

          {{- if .command }}
          command:
            {{- range .command }}
            -  {{ . }}
            {{- end }}
          {{- end }}
          {{- if .args }}
          args:
            {{- range .args }}
            -  {{ . }}
            {{- end }}
          {{- end }}
          {{- if .env }}
          env:
          {{- with .env }}
            {{- range $name, $value := . }}
            {{- if not (empty $value) }}
            - name: {{ $name | quote }}
              value: {{ $value | quote }}
            {{- end }}
            {{- end }}
          {{- end }}
          {{- end }}
          {{- if .configMapEnvFrom.name }}
          envFrom:
            - configMapRef:
                name: {{ .configMapEnvFrom.name }}
          {{- end }}
          {{- with .resources }}
          resources: {{ toYaml . | nindent 12 }}
          {{- end }}
          ports:
            {{- range $port := .containerPorts }}
            - name: {{ $port.name }}
              containerPort: {{ $port.containerPort }}
              protocol: {{ $port.protocol }}
            {{- end }}

          {{- if .livenessProbe.enabled }}
          {{- with .livenessProbe }}
          livenessProbe:
            {{ template "probeSpec" . }}
          {{- end }}
          {{- end }}

          {{- if .readinessProbe.enabled }}
          {{- with .readinessProbe }}
          readinessProbe:
            {{ template "probeSpec" . }}
          {{- end }}
          {{- end }}
        {{- end }}
{{- end -}}
