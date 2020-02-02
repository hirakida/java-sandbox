## Replica Set

```
% docker-compose up -d
% mongo --port 27017

> config = {"_id":"rs1","members":[{"_id":0,"host":"mongo01:27017"},{"_id":1,"host":"mongo02:27017"},{"_id":2,"host":"mongo03:27017"}]}
> rs.initiate(config)
```
