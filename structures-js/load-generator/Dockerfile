# Build stage
FROM node:22-slim AS builder
RUN corepack enable
WORKDIR /app
COPY package.json ./
COPY pnpm-lock.yaml ./
RUN pnpm install
COPY . .
RUN pnpm run build

# Production stage
FROM node:22-slim
RUN corepack enable
WORKDIR /app
COPY --from=builder /app .
ENV NODE_ENV production
CMD ["node", "./dist/main.mjs"]
