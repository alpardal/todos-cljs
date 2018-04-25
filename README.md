# TODOs

Exemplo simples de TODOs em ClojureScript usando [re-frame](https://github.com/Day8/re-frame).

## Dependências

Java 8+ e [Leiningen](https://leiningen.org/).

## Rodando

```sh
lein figwheel
```

E acesse: [http://localhost:3449](http://localhost:3449).

Para acessar o namespace de eventos (como `e`) e o `default-db` no repl:

```clojure
  (require '[todos.events :as e])
  (use '[todos.db :only (default-db)])
```
