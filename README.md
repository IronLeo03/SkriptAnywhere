# SkriptAnywhere

Skript is a plugin for minecraft servers that has been developed to allow server owners to create **small** additions for their servers without the need of a third party.
Skript is often mistakenly abused (REALLY used too much for too big things) and often taken as an actual java alternative.
Therefore I have decided to create an addon to allow more improper usage of skript.
Sure, there MIGHT be some cases where this is of use. But better not...

## Features

- Server Sockets
    - Handle connections on any port using skript
- Sockets
    - Connect your server to another listening server!
### Todo
- Java FX applications
- ???

## Usage
### Server sockets
Expression -> Create server
```
Pattern: "[a] [new] server listening on port %integer%", returns "anywhereserversocket"
set {server} to a new server listening on port 25570
```

Expression -> Get server error
(Handle errors within skript. Errors are always thrown in console)
```
Property "error" from type "anywhereserversocket"
set {error} to the server's error
```

Expression -> Get server port
```
Property "port" from type "anywhereserversocket"
set {port} to the server's port
```

Expression -> The server
```
Pattern: "[the] server", returns "anywhereserversocket"
Available in: Server Accepts Connection
set {error} to the server's error
```

Effect -> Server Start Accept
```
Pattern: "(accept|listen) [on] %anywhereserversocket%"
```

Effect -> Server Stop
```
Pattern: "stop [listening] [on] %anywhereserversocket%"
```

Event -> On server accepts a new connection
```
Pattern: "(new connection|receive connection) [(on|at|to) [port] %-integer%] [[and] client filter [of] %-string%]"
```

### Sockets

Expression -> Create client
```
Pattern: "[a] [new] client [connected] [to] %string% [on] [port] %integer%", returns "anywheresocket"
set {client} to a new client connected to "localhost" on port 25565
```


Expression -> Get client data
```
Property "data" or "message" from type "anywheresocket"
set {data} to the client's data
```

Expression -> Get client filter
```
Property "filter" from type "anywheresocket"
set {filter} to the client's filter
```

Expression -> Is client active
```
Property "active" from type "anywheresocket"
set {isActive} to the client's active
```

Expression -> The client
```
Pattern: "[the] client", returns "anywheresocket"
Available in: Server Accepts Connection, Client Connected, Client Receives Data, Client Disconnected
set {client} to the client
```

Effect -> Make client connect
```
Pattern: [make] %anywheresocket% connect[s]"
```

Effect -> Filter socket
```
Pattern: "filter %anywheresocket% by %string%"
```

Effect -> Client Stop
```
Pattern: "stop [the] (connection|socket) %anywheresocket%"
```

Effect -> Send data to client
```
Pattern: "send %string% (to [the] socket of|using [[the] client['s socket]]) %anywheresocket%"
```

Event -> Client Connects
```
Pattern: "client connects"
```

Event -> Client Disconnects
```
Pattern: "client disconnects"
```

Event -> Client Receives Data
```
Pattern: "client [[with] filter %-string%] receive[s]"
```

# Examples

HTTP Server (Please no)
```
on load:
	set {server} to server listening on port 25565
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
		send formatHTTPResponse("<h1>The webserver works!</h1><h3>But something's wrong... I can feel it...</h3>") using the client
	else:
		send "I still didn't implement the effect to forcefully disconnect. Go away." to the socket of the client
```