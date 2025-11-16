# Use Node.js 25
FROM node:25-alpine

WORKDIR /app

# Copy package files
COPY package*.json ./

# Install dependencies
RUN npm ci

# Copy source code
COPY . .

# Build the application
RUN npm run build

# Expose port 4200
EXPOSE 4200

# Start the application
CMD ["npm", "start", "--", "--host", "0.0.0.0", "--port", "4200"]
