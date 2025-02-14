# To learn more about how to use Nix to configure your environment
# see: https://developers.google.com/idx/guides/customize-idx-env
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-23.11"; # or "unstable"
  services = {
    mysql = {
      enable = true;
    };
  };
  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.zulu17
    pkgs.maven
    pkgs.docker-compose
    pkgs.nettools 
    # pkgs.nodejs_18
    # pkgs.socat
    pkgs.mysql80
  ];
  # Sets environment variables in the workspace
  env = {
    DATABASE_URL = "jdbc:mysql://localhost:3306/my_database?connectTimeout=5000&socketTimeout=5000";
    DATABASE_USER = "root";
    DATABASE_PASSWORD = "root_password";
  };
  services.docker.enable = true;
  idx = {
    # Search for the extensions you want on https://open-vsx.org/ and use "publisher.id"
    extensions = [
      "vscjava.vscode-java-pack"
      "rangav.vscode-thunder-client"
    ];
    workspace = {
      onCreate = {
        install = ''
          cd backend && mvn clean install # Build backend
          cd .. && cd frontend && npm install # Install frontend deps
        '';
      };
      onStart = {
      runServer = ''
            cd docker && docker-compose up -d             
            cd ../backend && mvn spring-boot:run &> /dev/null &
            cd ../frontend && ng serve 
            # cd backend && mvn spring-boot:run &> /dev/null &
            # cd ../frontend && ng serve 
        '';
      };
      
    };
  };
}
