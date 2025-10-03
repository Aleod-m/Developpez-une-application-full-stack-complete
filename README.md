# MMD 

A Spring Boot + Angular 14 application.

## Environement. 

Assure that a MySql database is running and you put the initial data present in ressources/sql

## Back.

Assure that you filled the database credentials in the `application.properties` file.
The if you are deploying the application change the `oc.app.JwtSecret` key

Then run:
```sh
mvn spring-boot:run
```
## Front.

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 14.1.3.

Don't forget to install your node_modules before starting (`npm install`).

### Development server

Run `npm run start` for a dev server. Navigate to `http://localhost:4200/`. The application will automatically reload if you change any of the source files.

### Build

Run `npm run build` to build the project. The build artifacts will be stored in the `dist/` directory.

