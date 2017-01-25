# StarWars - Character #

Aplicação que permite visualizar informações de personagem dos filmes Star Wars.
Para adicionar novos personagens, deve ser lido uma URL a partir de QR Code.
Acesse [vctapps.com/sw-qrcodes](http://www.vctapps.com/sw-qrcodes/) e veja alguns QR Codes disponíveis.

### Arquitetura utilizada ###

* MVP

### Libraries ###

* Retrofit (Consumo de web services)
* Vision (Captura de QR Codes)
* Fusion (Geolocalização)
* Picasso (Download e Cache dos posters)
* Dexter (Solicitar permissões)

### Patterns ###

* Observe
* Singleton

### Features ###

* Leia a URL dos personagem a partir de QR Codes
* Captura da geolozalização no momento da captura do QR Code
* Visualize informações físicas do personagem
* Visualize quais filmes ele participou
* Cache das informações. Visualize mesmo offline.
* Internacionalização

### TODO ###

* Mudar carregamento das imagens da tela de login para em tempo de execução, para poder melhorar a qualidade da imagem e não pesar na memória
* Na classe ManagerRegisters, implementar a resposta do Retrofit para executar tudo em uma Thread diferente para não sobrecarregar UIThread (Está sendo feito acesso ao BD por ela, grave)