

FROM node:20-alpine AS builder

COPY package*.json ./

RUN npm install

# Copy the entire source code into the container
COPY . .

RUN npm run build

FROM nginx:alpine

# Kopira dist folder sadržaj sa stroja u nginx folder u Docker containeru
COPY --from=builder dist /usr/share/nginx/html

# Izlozi port
EXPOSE 80

# Kopira konfiguraciju nginx-a
COPY nginx.conf /etc/nginx/conf.d/default.template

# postavlja entrypoint
ENTRYPOINT ["/bin/sh", "-c", "envsubst '${BACKEND_URL}' < /etc/nginx/conf.d/default.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
