package controles;

import java.util.Arrays;

public class ValidacaoDeCampos {

    private String campo;

    public Integer retorno = 0;

    public ValidacaoDeCampos(String texto, Integer tipo) {
        setCampo(texto);
        switch (tipo) {
            case 1:
                validarColuna();
                break;
            case 2:
                validarPaginacao();
                break;
            case 3:
                validarOpcoes();
                break;
            default:
                validarLetras();
                break;
        }
    }

    private void validarColuna() {
        if(!getCampo().equals("0")) {

            if(!getCampo().matches("([\\d,]*\\d)$")) {
                System.out.println("É permitido apenas números separados por vírgula em sequência.\n");
                setRetorno(1);
            }
            else {
                String[] texto = getCampo().split(",");
                Object[] objeto = Arrays.stream(texto).distinct().toArray();
                Integer numero = 2;
                if(objeto.length < texto.length) {
                    System.out.println("Não é permitido números repetidos.\n");
                    numero = 1;
                }
                else {
                    for (Integer i = 0; i < texto.length; i++) {
                        if (texto[i].isEmpty()) {
                            System.out.println("Não é permitido campo vazio.\n");
                            numero = 1;
                        }
                        else if (texto[i].length() > 2) {
                            System.out.println("Não é permitido campo acima de 2 casas.\n");
                            numero = 1;
                            i = texto.length;
                        }
                        else if (Integer.parseInt(texto[i]) < 1 || Integer.parseInt(texto[i]) > 18) {
                            System.out.println("Você deve digitar apenas as opções listadas.\n");
                            numero = 1;
                            i = texto.length;
                        }
                    }
                }
                setRetorno(numero);
            }
        }
        else {
            setRetorno(2);
        }
    }

    private void validarPaginacao() {
        if(!getCampo().equals("0")) {
            Integer numero = 0;
            if (!getCampo().matches("^([1-9]\\d{0,8})$")) {
                System.out.println("Em exibição de linhas Digite apenas números e valores acima de 0" +
                        " e abaixo de 1.000.000.000\n");
                numero = 2;
            }
            else if (getCampo().length() > 9) {
                System.out.println("Não é permitido campo acima de 9 casas.\n");
                numero = 2;
            }
            setRetorno(numero);
        }
        else {
            setRetorno(0);
        }
    }

    private void validarOpcoes() {
        switch (getCampo()) {
            case "0":
                setRetorno(0);
                break;
            case "1":
                setRetorno(1);
                break;
            case "2":
                setRetorno(2);
                break;
            default:
                setRetorno(9);
                break;
        }
    }

    private void validarLetras() {
        if(!getCampo().matches("^[\\w\\s&&[^\\d]]+$")) {
            System.out.println("Digite apenas letras.\n");
            setRetorno(1);
        }
        else {
            setRetorno(0);
        }
    }

    public String getCampo() {
        return campo;
    }

    public void setCampo(String campo) {
        this.campo = campo;
    }

    public Integer getRetorno() {
        return retorno;
    }

    public void setRetorno(Integer retorno) {
        this.retorno = retorno;
    }
}
