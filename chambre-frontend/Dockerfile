# Stage 1: Build the React/Vite application using Node 18 (which supports crypto.getRandomValues)
FROM node:18-alpine as builder
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy all source code and build the app (Vite's build output goes to "dist")
COPY . .
RUN npm run build

# Stage 2: Serve the built application with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Clean the default Nginx content
RUN rm -rf ./*

# Copy the build output from the builder stage using a wildcard, so you don't need to hard-code the folder name
COPY --from=builder /app/dist .

# Expose port 80
EXPOSE 80

# Start Nginx in the foreground
CMD ["nginx", "-g", "daemon off;"]
