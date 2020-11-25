# InternshipProgram Postman collections and tests

## Overview

This directory contains:

* All internships program runnable and sample postman collections
* All postman environments and their global variables
* All assets (files) used as inputs in the postman sample requests and tests

## Usage

Postman test collections can be imported in local postman engine or can be executed directly from the docker container. 

### Using from local postman engine

* Import all collections and environments (except the docker-dev one) in your local postman
* Put the assets files to absolute location: `/postman/internship-programs` (for windows inside `C:/postman/internship-programs`)

### Using inside docker container

* Execute shell command: `docker-compose up --build` from the same path where this readme file is located (same level as docker-compose.yml and dockerfile).

**!!!Important: In order to successfully execute tests agains dev environment (currently only supported environment), 
please build and run dev containers on the main app (read in the root readme file how to run them). 
This compose contains connection to the dev network in order to execute the tests against the dev api service.**

#### Results

After executing the tests in docker, the actual results can be read from the console or from the html
output file located in `./reports/test-result.html`