## Replica Set

```
% docker-compose up -d
% mongo --port 27017

> rs.initiate({
  "_id":"rs1",
  "members":[{"_id":0,"host":"mongo01:27017"}]
})

rs1:PRIMARY> rs.add("mongo02:27017")

rs1:PRIMARY> rs.addArb("mongo03:27017")
```
