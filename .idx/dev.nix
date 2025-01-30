# To learn more about how to use Nix to configure your environment
# see: https://developers.google.com/idx/guides/customize-idx-env
{ pkgs, ... }: {
  # Which nixpkgs channel to use.
  channel = "stable-23.11"; # or "unstable"
  # Use https://search.nixos.org/packages to find packages
  packages = [
    pkgs.zulu17
    pkgs.maven
    pkgs.docker-compose
    pkgs.nettools 
  ];
  # Sets environment variables in the workspace
  env = {};
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

            # Wait for backend and frontend to be ready 
            #wait-for -q localhost:8080 # Backend port
            #wait-for -q localhost:4200 # Frontend port
        '';
      };
      
    };
  };
}
