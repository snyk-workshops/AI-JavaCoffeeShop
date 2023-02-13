# Hint 1

You can build the image with `docker-compose build`

The image it creates is `javacoffeshop_javacofeeshop`

You can use the Snyk CLI and do something like
```bash
snyk container test javacoffeshop_javacofeeshop --file=Dockerfile
```