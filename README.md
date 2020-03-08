# oddities service

## Execution instructions

`docker run --name [CONTAINER_NAME] -p [ACCESS_PORT]:8088 kovta/odd-svc`

example:

`docker run --name odd-svc-1 -p 8088:8088 kovta/odd-svc`

### Security considerations

Proper token based server side security can potentially be setup with a targeted cloud provider. 
Most vendors provide an option to register an OAuth2 client, after which we can set it as a authorization and token URI endpoint in Spring.

### Redundancy considerations

Once the service reaches maturity orchestration tools must be considered to meet the growing user load with reliability and availability.

Using Kubernetes with autoscalers would be a viable solution. 
Most cloud vendors provide a K8s engine to be used for such purposes but it can also be set up locally.
This flexibility would make a hybrid cloud infrastructure foundation possible reducing the risk of issues arising from single vendor failures. 