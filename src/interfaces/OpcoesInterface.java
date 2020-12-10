package interfaces;

import controles.ValidacaoDeCampos;
import servicos.CsvServicos;

import java.util.Scanner;

public class OpcoesInterface {

    private String filtroColuna;

    private String filtroPaginacao;

    public OpcoesInterface() {
        Scanner entrada = new Scanner(System.in);
        Integer contadorLeitura = 1;
        while (contadorLeitura > 0) {
            if(contadorLeitura == 1) {
                System.out.println("" +
                        "Escolha as colunas que deseja visualizar. " +
                        "Separe as por virgula, exemplo: 1,2,3 " +
                        "Ou se preferir digite 0 para todas:");
                System.out.println("1 = city (cidade)");
                System.out.println("2 = city_ibge_code (código IBGE)");
                System.out.println("3 = date (data)");
                System.out.println("4 = epidemiological_week (semana epidemiológica)");
                System.out.println("5 = estimated_population (população estimada)");
                System.out.println("6 = estimated_population_2019 (população estimada em 2019)");
                System.out.println("7 = is_last");
                System.out.println("8 = is_repeated");
                System.out.println("9 = last_available_confirmed (última confirmação disponível)");
                System.out.println("10 = last_available_confirmed_per_100k_inhabitants " +
                        "(última confirmação disponível para 100k de habitantes)");
                System.out.println("11 = last_available_date (última data disponível)");
                System.out.println("12 = last_available_death_rate (última taxa de mortalidade disponível)");
                System.out.println("13 = last_available_deaths (última morte disponível)");
                System.out.println("14 = order_for_place (pedido para lugar)");
                System.out.println("15 = place_type (tipo de lugar)");
                System.out.println("16 = state (estado)");
                System.out.println("17 = new_confirmed (nova confirmação)");
                System.out.println("18 = new_deaths (novas mortes)");
                setFiltroColuna(entrada.next().trim());
                ValidacaoDeCampos validar = new ValidacaoDeCampos(getFiltroColuna(), 1);
                contadorLeitura = validar.getRetorno();
            }
            if(contadorLeitura == 2) {
                System.out.println("Defina um número de linhas a exibir ou digite 0 para paginação padrão: ");
                setFiltroPaginacao(entrada.next().trim());
                ValidacaoDeCampos validar = new ValidacaoDeCampos(getFiltroPaginacao(), 2);
                contadorLeitura = validar.getRetorno();
            }
            if(contadorLeitura == 0) {
                new CsvServicos(getFiltroColuna(),getFiltroPaginacao());
            }
        }
    }

    public String getFiltroColuna() {
        return filtroColuna;
    }

    public void setFiltroColuna(String filtroColuna) {
        this.filtroColuna = filtroColuna;
    }

    public String getFiltroPaginacao() {
        return filtroPaginacao;
    }

    public void setFiltroPaginacao(String filtroPaginacao) {
        this.filtroPaginacao = filtroPaginacao;
    }
}
