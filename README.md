# SkriptAnywhere

Skript is a plugin for minecraft servers that has been developed to allow server owners to create **small** additions for their servers without the need of a third party.
Skript is often mistakenly abused (used when it shouldn't) and often taken as an actual java alternative.
I have decided to create an addon to allow more improper usage of skript.
Sure, there MIGHT be some cases where this is of use. But I can't see any.

## Features

- Server Sockets
    - Handle connections on any port using skript
- Sockets
    - Connect your server to another listening server

## Usage

### Server sockets
#### Create server socket (expression)

> **Definition**
> 
> Pattern: `[a] [new] server listening on port %integer%`
> 
> Return Type: `AnywhereServerSocket`

Remember that creating a server socket does not automatically start it.

**Example:**
```
set {server} to a new server listening on port 1337
```

#### The server (expression)

> **Definition**
>
> Pattern: `[the] server`
>
> Available on `Server Accepts Connection`
> 
> Return Type: `AnywhereServerSocket`

#### Server Start Accept (effect)
> **Definition**
>
> Pattern: `(accept|listen) [on] %anywhereserversocket%`

Remember that Skript cannot store instances of AnywhereServerSocket through restarts. You will also get a warning from Skript about this.

**Example:**
```
set {server} to server listening on port 1337
listen on {server}
```

#### Server Accepts Connection (event)

> **Definition**
>
> Pattern: `(new connection|receive connection) [(on|at|to) [port] %-integer%]`

### Sockets

#### Client Receives Data (event)

> **Definition**
>
> Pattern: `client receive[s]`

#### Send Data To Client (event)

> **Definition**
>
> Pattern: `send (packet|data|network reply) %string% [to [the] socket of|using [[the] client['s socket]]|to] %anywheresocket%"`

**Example:**

```
send packet "Here is your reply!" to the socket of the client
```

#### Is Client Active (property)

> **Definition**
>
> Pattern: `active`
> 
> Of: `AnywhereSocket`

**Example:**

```
set {a} to {client}'s active
```

#### Received Message Content (expression)

> **Definition**
>
> Pattern: `"[the] [client['s]] data"`
>
> Available on `Client Receives Data`
>
> Return Type: `String`

**Example:**

```
on client receive:
	set {client} to the client
	set {data} to client's data
```

#### The Client (expression)

> **Definition**
>
> Pattern: `[the] client`
>
> Available on `Server Accepts Connection`, `Client Receives Data`, `Client Connects`
>
> Return Type: `AnywhereSocket`

## Example code

### HTTP server

```
on load:
  set {server} to server listening on port 1337
  listen on {server}


function formatHTTPResponse(html: text) :: text:
  broadcast {html}
  set {html.length} to length of "%{_html}%"
  set {_response::1} to "HTTP/1.1 200 OK"
  set {_response::2} to "Server: Skript Server (aka doom)"
  set {_response::3} to "Content-Length: %{html.length}%"
  set {_response::4} to ""
  set {_response::5} to {_html}

  set {toReturn} to ""
  loop {_response::*}:
    set {toReturn} to "%{toReturn}%%loop-value%%newline%"
  return "%{toReturn}%"

on client receive:
  set {data} to the client's data
  if the first 10 characters of {data} are "GET / HTTP":
    send packet formatHTTPResponse("<h1>The webserver works!</h1><h3>But something's wrong... I can feel it...</h3>") using the client
  else:
    send packet "I still didn't implement the effect to forcefully disconnect" to the client
```