{
  inputs = {
      nixpkgs.url = "github:cachix/devenv-nixpkgs/rolling";
      devenv.url = "github:cachix/devenv";
  };

  outputs = {self, flake-parts, nixpkgs, ...} @ inputs:
  flake-parts.lib.mkFlake {inherit inputs;} {
    imports = [ inputs.devenv.flakeModule ];
    systems  = nixpkgs.lib.systems.flakeExposed;
    perSystem = {config, self', inputs', pkgs, system, ...}: {
      devenv.shells.default = {
        packages = with pkgs; [
          nodejs_22
          typescript-language-server
          jdt-language-server
          mysql84
          jdk17
          maven
          lombok
          # Cypress deps
          # cypress
        ];
        # env.CYPRESS_INSTALL_BINARY=0;
        # env.CYPRESS_RUN_BINARY="${pkgs.cypress}/bin/Cypress";
        env.JDTLS_JVM_ARGS="-javaagent:${pkgs.lombok}/share/java/lombok.jar";
        scripts = {
          db-conn.exec = "${pkgs.mysql84}/bin/mysql -u root";
        };
        services.mysql = {
          enable = true;
          package = pkgs.mysql84;
          initialDatabases = [ 
            { name = "mddapi"; schema = ./ressources/sql/prod.sql; } 
            { name = "mddapi-test"; schema = ./ressources/sql/test.sql; } 
          ];
          ensureUsers = [ 
            {
               name = "db-admin";
               password = "123456"; 
               ensurePermissions = {
                 "test.*" = "ALL PRIVILEGES";
               };
            } 
          ];
        };
      };
    };
  };
}
