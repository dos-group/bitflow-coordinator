# Setting up the Openstack Infrastructure

## Openstack

### Openstack CLI in Windows

Use the batch-file 'Windows-setup.bat' to initialize all environment variables you need to establish a connection to the CIT-Openstack-Cloud.

### Openstack CLI in Unix

Use instead the file 'Unix-setup.bat' to setpu the environment variables.

## Creating servers in Openstack

### Creating a network

see https://docs.openstack.org/horizon/latest/user/create-networks.html

### Creating a security group

Creating the security group and then adding rules which allow ssh and icmp from everywhere. In the end check if everything worked.

```shell
openstack security group create <group-name> --description <description>
openstack security group rule create <group-name> --protocol tcp --dst-port 22:22 --remote-ip 0.0.0.0/0
openstack security group rule create <group-name> --protocol icmp
openstack security group rule list <group-name>
openstack security group list
```

### Create a key pair for SSH-connections

Creating the keypair by using the Openstack GUI won't work to a known issue. Instead use the command
```shell
ssh -t rsa -f <keyname>
```

Then, using the Openstack GUI, import the created public key.

* Using Putty Key Generator also does not seem to work

### Creating server instance

Run the following command (and specify an instance name)

```shell
openstack server create --flavor m1.smaller --image ubuntu-16.04 --security-group bitflow_backend_sec_group --network internal-net --key-name bitflow-backend-key <instanceName>
```

For assigning a Floating IP to the created instance I recommend using the Openstack GUI again or use the following commands.
```shell
openstack floating ip create tu-internal
openstack floating ip list
openstack server add floating ip <instanceName> <floating-ip>
```

## Setting up the instances

### Connecting to the instance

Use SSH to connect to the server instance by running (now use your private key generated before):

```shell
ssh -i <path to private key> ubuntu@<instance IP>
```

(You can also use PuTTY for the connection just remember that putty wants its own key format)

### Setting up a MySQL database

```shell
sudo apt update
sudo apt-get update
sudo apt-get install mysql-server
mysql -u root -p
mysql> create database <database name>;
mysql> create user 'admin'@'%' identified by 'admin';
mysql> grant all privileges on *.* to 'admin'@'%' with grant option;
```

The root user won't be able to access the database remotely, so we create a new user 'admin'.

To make the database accessible over network navigate to '/etc/mysql/mysql.conf.d' and open the file 'mysqld.cnf'. Change the entry

```shell
bind-address = 127.0.0.1
```

to 

```shell
bind-address = <IP address of the server>
```
It is important to not use the Floating IP but the actual IP of the instance (you can use 'ifconfig' to retrieve the IP)
Then restart your mysql server by running

```shell
sudo mysql service restart
```

You should now be able to access your database remotely.

### Setting up process agents

To install the process agents:

First install the bitflow-pipeline tool following the instructions here: https://gitlab.tubit.tu-berlin.de/cit-master-project/go-bitflow-pipeline  
Then you should have Go installed and can install the process agent with: go get github.com/antongulenko/bitflow-process-agent

To allow traffic from the specific port they need to be added to the specific Openstack security group

To run process agent:

```shell
bitflow-process-agent -h :<port-number>
```

To update the process agents:

```shell
go get -u -v github.com/antongulenko/bitflow-process-agent
```



