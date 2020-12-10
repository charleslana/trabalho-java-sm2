package servicos;

import controles.ValidacaoDeCampos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CsvServicos {

    private Integer tamanhoColuna = 18;
    private Integer tamanhoPagina = 10;
    private String[] tipoColunas = {
            "1","2","3","4","5","6","7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18"
    };
    private String campoOpcoes;

    public CsvServicos(String colunas, String paginas) {
        String[] texto = colunas.split(",");
        if(!colunas.equals("0")) {
            setTamanhoColuna(texto.length);
            setTipoColunas(texto);
        }
        if(!paginas.equals("0")) {
            setTamanhoPagina(Integer.parseInt(paginas));
        }
        BufferedReader ler = null;
        try {
            ler = new BufferedReader(new FileReader("caso_full.csv", StandardCharsets.UTF_8));
            String colunaCsv;
            Integer contarColuna = 0;
            Integer contarPagina = 1;
            String[] filtro = new String[getTamanhoColuna()];
            ArrayList<String> lista = new ArrayList();
            StringBuilder pre = new StringBuilder();
            while ((colunaCsv = ler.readLine()) != null) {
                if(contarColuna <= getTamanhoPagina()) {
                    colunaCsv = colunaCsv.replaceAll("'", " ");
                    for (Integer i = 0; i < getTipoColunas().length; i++) {
                        filtro[i] = String.valueOf(colunaCsv.split(",")
                                [Integer.parseInt(getTipoColunas()[i]) - 1]);
                    }
                    pre.setLength(0);
                    Arrays.stream(filtro).forEach(coluna -> {
                        pre.append("'" + coluna + "'" + ",");
                        if (coluna.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
                            LocalDate localDate = LocalDate.parse(coluna);
                            String formatado = localDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                            coluna = formatado;
                        }
                        System.out.printf("%-50s", coluna);
                    });
                    System.out.println("");
                    pre.setLength(pre.length() - 1);
                    lista.add(pre.toString());
                    contarColuna++;
                }
                else {
                    contarPagina++;
                    Scanner entrada = new Scanner(System.in);
                    System.out.println("\nEscolha as opções a seguir:\n0 - Continuar\n1 - Alterar paginação" +
                            "\n2 - Exportar para SQLite\n9 - Cancelar");
                    setCampoOpcoes(entrada.next().trim());
                    ValidacaoDeCampos validar = new ValidacaoDeCampos(getCampoOpcoes(), 3);
                    if(validar.getRetorno() == 0) {
                        System.out.println("Página: " + contarPagina + "\n");
                        contarColuna = 1;
                    }
                    else if(validar.getRetorno() == 1) {
                        System.out.println("Defina a quantidade de linhas exibidas na página: ");
                        setCampoOpcoes(entrada.next().trim());
                        ValidacaoDeCampos validarPagina = new ValidacaoDeCampos(getCampoOpcoes(), 2);
                        if(validarPagina.getRetorno() == 0) {
                            setTamanhoPagina(Integer.parseInt(getCampoOpcoes()));
                            System.out.println("Página: " + contarPagina + "\n");
                            contarColuna = 1;
                        }
                        else {
                            break;
                        }
                    }
                    else if(validar.getRetorno() == 2) {
                        System.out.println("Defina um nome para o banco de dados sqlite (somente letras): ");
                        setCampoOpcoes(entrada.next().trim());
                        ValidacaoDeCampos validarNome = new ValidacaoDeCampos(getCampoOpcoes(), 4);
                        if(validarNome.getRetorno() == 0) {
                            new CsvExportacao(lista, getCampoOpcoes());
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    else {
                        break;
                    }
                }
            }
            System.out.println("Consulta finalizada.");

//            for (Integer i = 0; i < lista.size(); i++) {
//                System.out.println(lista.get(i));
//            }
            ler.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer getTamanhoColuna() {
        return tamanhoColuna;
    }

    public void setTamanhoColuna(Integer tamanhoColuna) {
        this.tamanhoColuna = tamanhoColuna;
    }

    public Integer getTamanhoPagina() {
        return tamanhoPagina;
    }

    public void setTamanhoPagina(Integer tamanhoPagina) {
        this.tamanhoPagina = tamanhoPagina;
    }

    public String[] getTipoColunas() {
        return tipoColunas;
    }

    public void setTipoColunas(String[] tipoColunas) {
        this.tipoColunas = tipoColunas;
    }

    public String getCampoOpcoes() {
        return campoOpcoes;
    }

    public void setCampoOpcoes(String campoOpcoes) {
        this.campoOpcoes = campoOpcoes;
    }

}
