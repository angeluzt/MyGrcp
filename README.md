# GRPC (google Remote Procedure Call)
gRPC, or Google Remote Procedure Call, is an open-source framework that facilitates 
efficient and cross-language communication between distributed systems. It is designed 
to simplify the development of high-performance, reliable, and scalable remote services 
by using a language-agnostic protocol called Protocol Buffers. gRPC allows applications 
written in different languages to seamlessly communicate, offering features like 
bidirectional streaming, multiplexing, and built-in support for authentication and load 
balancing. Developers define service methods and message types using Protocol Buffers, 
and gRPC generates client and server code in various languages, making it easier to build 
robust and interoperable distributed systems. Overall, gRPC provides a modern and 
efficient solution for building communication between microservices, IoT devices, and other 
distributed components in a network.

# Why gRPC is faster?
Because the request data is serialized **(binary wire format of Protocol Buffers)** and during 
most of the process is handled like that, this means is not required to use JSON, so we do 
not waist time while mapping this data to something that a user can understand. 

To achieve this, the proto service contains a group of request and responses that contains
the structure and also an is
```
message GreetingRequest {
    string message = 1;
}
```

Is using = 1; to set an id to each param, in this way is not required to deserialize just
to know with group of bytes belong to which param, in this way the order is not relevant.
> Is recommended to no duplicate these Ids in case a field is removed, or updated, in this 
> way we can achieve backwards compatibility 

# Steps to create a gRPC
## 1) Set Up a Gradle Project:
* Include the plugin:
    1. id "com.google.protobuf" version "0.9.4"

* Add the dependencies:
```
  implementation "io.grpc:grpc-api:${grpcVersion}"
  implementation "io.grpc:grpc-stub:${grpcVersion}"
  implementation "io.grpc:grpc-netty-shaded:${grpcVersion}"
  implementation "io.grpc:grpc-protobuf:${grpcVersion}"
```

* Add the source set location
```
sourceSets {
    src {
        main {
            java {
                srcDirs 'build/generated/source/proto/main/grpc'
                srcDirs 'build/generated/source/proto/main/java'
            }
        }
    }
}
```

* Configure protobuf plugin
```
protobuf {
    // configure protoc executable
    protoc {
        // download from repositories
        artifact = 'com.google.protobuf:protoc:3.12.2'
    }
    // Locate the codegen plugins
    plugins {
        // Locate a plugin with name 'grpc'. This step is optional.
        // If you leave it empty, it uses the current directory.
        // If you don't specify it, protoc will try to use "protoc-gen-grpc" from
        // system search path.
        grpc {
            artifact = "io.grpc:protoc-gen-grpc-java:${grpcVersion}"
        }
    }

    generateProtoTasks {
        all()*.plugins {
            grpc {}
        }
    }
}
```

* Write your service
  1. This service will be compiled using the plugin and result classes will be saved in the specified location
```
syntax = "proto3";

package org.example.greeting;

option java_multiple_files = true;
option java_package = "org.example";

service GreetingService {
  rpc greeting(GreetingRequest) returns (GreetingResponse){

  }
}

message GreetingRequest {
  string message = 1;
}

message GreetingResponse {
  string message = 1;
}
```

# How to call this service
```
grpcurl --plaintext -d '{"message":"How are you"}' localhost:9090 org.example.greeting.GreetingService/greeting
```

