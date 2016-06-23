var restify = require('restify');
require('console.table');

var server = restify.createServer({
  name: 'myapp',
  acceptable: ['json'],
  version: '1.0.0'
});
server.use(restify.acceptParser(server.acceptable));
server.use(restify.queryParser());
server.use(restify.bodyParser());

function handleRequest(req, res, next) {
  console.log("Requesting URL", req.url);
  console.table('Body', req.body);
  console.table('Params', req.params);
  console.table('Headers', req.headers);
  console.table('Query', req.query);
  return res.send({});
}

server.get('/:foo/', handleRequest);
server.post('/:foo', handleRequest);
server.head('/:foo', handleRequest);

server.listen(8080, function () {
  console.log('%s listening at %s', server.name, server.url);
});
