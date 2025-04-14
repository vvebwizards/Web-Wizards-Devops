# Stage 1: Build the React/Vite application
FROM node:16-alpine as builder
WORKDIR /app

# Copy package.json and package-lock.json to install dependencies
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the source code
COPY . .

# Build the app (Vite typically outputs to the "dist" directory)
RUN npm run build

# Stage 2: Serve the built application with Nginx
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Remove the default Nginx static assets (optional)
RUN rm -rf ./*

# Copy the build output from the builder stage
COPY --from=builder /app/dist .

# Expose port 80
EXPOSE 80

# Start Nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
