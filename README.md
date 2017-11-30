# bitflow-frontend

> frontend for the bitflow orchestration framework

## Build Setup

``` bash
# install dependencies
npm install

# serve with hot reload at localhost:8080
npm run dev

# build for production with minification
npm run build

# build for production and view the bundle analyzer report
npm run build --report

# run unit tests
npm run unit

# run all tests
npm test
```

For a detailed explanation on how things work, check out the [guide](http://vuejs-templates.github.io/webpack/) and [docs for vue-loader](http://vuejs.github.io/vue-loader).

## Deployment

The application is reachable under:
[10.200.2.70](10.200.2.70)

To *deploy* the application run 
```
make deploy
```

NOTE: If you have added npm modules, you need to run  
```
make npm-install
```
They are separate commands, because npm-install can take some time.

To check *application state* run
```
make monitor
```

To ssh onto the server use
```
ssh -i ops/tub-cit-frontend.key ubuntu@10.200.2.70
```