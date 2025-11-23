FROM node:22

RUN npm install -g @angular/cli@19

WORKDIR /app

COPY . .

RUN npm install

EXPOSE 4200

ENTRYPOINT ["ng", "serve", "--host", "0.0.0.0", "--poll", "1000"]
