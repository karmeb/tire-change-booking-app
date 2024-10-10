FROM node:20-alpine

RUN npm install -g http-server

WORKDIR /frontend

# 'package.json' and 'package-lock.json'
COPY package*.json ./
RUN npm install

# all the rest of files
COPY . .

RUN npm run build

EXPOSE 3000
CMD [ "http-server", "dist", "-p", "3000" ]