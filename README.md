## Running Project from CLI

    mvn spring-boot:run

## Monitoring with Prometheus

In WSL Ubuntu terminal, view all exposed IPs:

    hostname -I

- *example result:* `000.00.00.000`

In `smooky/docker/prometheus/prometheus.yml`, edit the IP to reflect:

    scrape_configs:
    - job_name: smooky
        metrics_path: /actuator/prometheus
        static_configs:
        - targets: ['000.00.00.000:8080']

Now we're ready to set up and run Prometheus & Grafana from the docker-compose.yml in Ubuntu terminal, run:

    docker compose up -d

In a browser you can now access:

1. Localhost Prometheus Endpoint | [http://localhost:8080/actuator/prometheus](http://localhost:8080/actuator/prometheus)

2. Prometheus | [http://localhost:9090/](http://localhost:9090/)

3. Grafana | [http://localhost:3000/](http://localhost:3000/)

For some reason, Ubuntu's [UFW](https://wiki.ubuntu.com/UncomplicatedFirewall) is blocking communication between Micrometer and Prometheus. To disable this in the Ubuntu terminal run: 

    sudo ufw disable

Don't forget to enable it when done!

    sudo ufw enable