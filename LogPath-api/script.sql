create table app.graph( 
grapId int NOT NULL GENERATED ALWAYS AS IDENTITY
,name varchar (50)
,distance int 
,linkId int
,PRIMARY KEY (grapId));

select * from GRAPH;

insert into APP.GRAPH( NAME, DISTANCE, LINKID ) values('Osasco', NULL, NULL);
insert into APP.GRAPH( NAME, DISTANCE, LINKID ) values('Barueri', 10, 1);

SELECT graph.grapId, graph.Name as "Graph", link.Name as "Link", graph.distance "Distance"
FROM APP.GRAPH graph
LEFT OUTER JOIN GRAPH link
ON graph.linkid = link.grapid;

TRUNCATE TABLE APP.GRAPH;

DROP TABLE APP.GRAPH;


SELECT GRAPID, NAME, DISTANCE, LINKID FROM APP.GRAPH WHERE (GRAPID = LINKID);