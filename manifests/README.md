Command to convet a cert into a jks file
keytool -importcert -trustcacerts -file amq-devops02-instance-cluster-ca-cert.crt -keystore amq-devops02-instance-cluster-ca-truststore.jks -storepass mypassword -alias amq-devops02-instance-cluster
