## <img src="meterial/imgSource/the_moment_README_logo.png"  width="300px">

["학교가 불편한 순간"](https://github.com/theMomentTeam/the_moment-Service)은 학교가 더욱 더 개선되기 위해 학생들 관점에서 느끼는 불편한 순간을 기록하는 공간입니다.  
> "the_moment" is a space that records the uncomfortable moments felt from the perspective of students in order to improve the school even more.

<br>

#### Installing Services - Ubuntu
```bash
#!/bin/bash

# apt update
sudo apt-get update -y

# docker install
sudo apt-get install docker.io -y 

# docker-compose install
sudo apt-get install docker-compose -y 

# java install
sudo apt-get install openjdk-11-jdk -y
```
#### Start Server - Ubuntu 
```bash
# move to server directory ex) ~/the-moment-server

# start the_moment-server use shell! (Docker Run)
# sudo ./docker-compose-env.sh

# Run in the background as well (prefer)
sudo nohup ./docker-compose-env.sh &
```

#### Development
```
* server dependency
    - Java 11 (Open-JDK)
    - SpringBoot
    - Spring Data JPA
    - H2 DataBase
    - Lombok
    - Validation
    - Spring Security
    - Spring Data Reactive Redis
    - Querydsl

* plugin
    - Spring Boot - 2.4.2
    - Dependency Management - 1.0.11.RELEASE
    - Querydsl - 1.0.10
    - Jacoco

* dev tools
    - IntelliJ IDEA
    - Docker
    - Redis
    - Swagger2

* Deployment
    - AWS EC2
    - AWS Route 53
    - AWS RDS (Mysql)
```

#### Our Server License
```
MIT License

Copyright (c) 2021 the_moment

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
