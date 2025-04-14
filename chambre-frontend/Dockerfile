# Stage 1: Build the React/Vite application using Node 18
FROM node:18-alpine as builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve the built application with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*
# Copy the build output
COPY --from=builder /app/dist .
# Copy your custom Nginx configuration (make sure nginx.conf is in the same folder as the Dockerfile or adjust the path)
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
