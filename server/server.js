var restify = require('restify');
require('console.table');

var server = restify.createServer({
  name: 'myapp',
  version: '1.0.0'
});
server.use(restify.acceptParser(server.acceptable));
server.use(restify.queryParser());
server.use(restify.bodyParser());

function handleRequest(req, res, next) {
  console.log("Requesting URL", req.url);
  console.log('body', req.body);
  console.table(req.params);
  console.table(req.headers);
  console.table(req.query);
  return res.send({});
}

server.get('/:foo/', handleRequest);
server.post('/:foo', handleRequest);

server.listen(8080, function () {
  console.log('%s listening at %s', server.name, server.url);
});
