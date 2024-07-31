# Command to convet a cert into a jks file
keytool -importcert -trustcacerts -file amq-instance-cluster-ca-cert.crt -keystore amq-instance-cluster-ca-truststore.jks -storepass mypassword -alias amq-instance-cluster

# Kafkacat Command
kafkacat -b amq-instance-kafka-bootstrap.amq-instance.svc:9093 -X security.protocol=SSL -X ssl.ca.location=/tmp/amq-instance-cluster-ca-cert.crt -X ssl
.endpoint.identification.algorithm=none   -X enable.ssl.certificate.verification=true -t topic1
% Auto-selecting Consumer mode (use -P or -C to override)
