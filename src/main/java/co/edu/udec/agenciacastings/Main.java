package co.edu.udec.agenciacastings;

import co.edu.udec.agenciacastings.application.AgenciaCastingService;
import co.edu.udec.agenciacastings.domain.model.AgenteCasting;
import co.edu.udec.agenciacastings.domain.model.Candidato;
import co.edu.udec.agenciacastings.domain.model.Casting;
import co.edu.udec.agenciacastings.domain.model.Cliente;
import co.edu.udec.agenciacastings.domain.model.Fase;
import co.edu.udec.agenciacastings.domain.model.Perfil;
import co.edu.udec.agenciacastings.domain.model.Prueba;
import co.edu.udec.agenciacastings.domain.model.Representante;
import co.edu.udec.agenciacastings.domain.model.Resultado;
import co.edu.udec.agenciacastings.domain.model.Seleccion;
import co.edu.udec.agenciacastings.infrastructure.persistence.RepositorioEnMemoria;
import co.edu.udec.agenciacastings.infrastructure.ui.Consola;
import co.edu.udec.agenciacastings.infrastructure.ui.MenuPrincipal;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AgenciaCastingService servicio = crearServicio();
        Consola consola = new Consola(new Scanner(System.in), System.out);
        new MenuPrincipal(servicio, consola).iniciar();
    }

    public static AgenciaCastingService crearServicio() {
        return new AgenciaCastingService(
                new RepositorioEnMemoria<>(Cliente::getId),
                new RepositorioEnMemoria<>(Casting::getId),
                new RepositorioEnMemoria<>(Fase::getId),
                new RepositorioEnMemoria<>(Prueba::getId),
                new RepositorioEnMemoria<>(Candidato::getId),
                new RepositorioEnMemoria<>(Perfil::getId),
                new RepositorioEnMemoria<>(Representante::getId),
                new RepositorioEnMemoria<>(AgenteCasting::getId),
                new RepositorioEnMemoria<>(Resultado::getId),
                new RepositorioEnMemoria<>(Seleccion::getId));
    }
}
