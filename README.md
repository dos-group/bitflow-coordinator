# CIT Master Project: Backend

# Group Infos

* Erik Daniel
* Hendrik Motza
* Sven Carlin

# Implemented API-Methods (basepath bitflow/ currently deployed on 10.200.1.139:8080)

* GET projects
* GET project/{id}
* GET project/{id}/users
* GET project/{id}/pipelines
* POST project/{id}/pipeline (create new pipeline in project)
* GET project/{id}/pipeline/{id}
* POST project/{id}/pipeline (add new Pipeline to project)
* DELETE project/{id}/users/{id} (remove user from project)
* POST project/{id}/users/{id} (assign user to project)
* GET users
* GET user/{id}
* DELETE user/{id}
* POST user/{id} (update user)
* POST user (create new user)
* GET info
* POST login
* POST user/{id}/changePassword
* POST project
* POST project/{id}
* GET capabilities
* GET project/{id}/pipeline/{id}/history
* GET project/{id}/pipeline/{id}/history/last 
* POST project/{id}/pipeline/{id}/start (working with restrictions)


# API-Methods not yet working

* POST project/{id}/pipeline/{id} (update pipeline in project)

# description of implemented features

see Implementation/swagger.json

# installation instructions

see Implementation/README.md for Wildfly setup instructions
see Infrastructure/README.md for Openstack setup as well as MySQL database set up