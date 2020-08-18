package application;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class Util {

    public static void addMessageInfo(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }

    public static void addMessageWarn(String message) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, message, null));
    }

    public static void addMessageError(String message) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR, message, null));
    }

    // Limpa todos os caracteres que não são números
    public static String clearNumberText(String string) {

        if (string == null)
            return null;

        if (string.trim().equals(""))
            return "";

        return string.replaceAll("[^0-9]", "");
    }

    // Retorna true caso os valores sejam iguais, e false caso nao sejam
    public static boolean confereStringIguais(String string1, String string2) {

        if(string1.equals(string2))
            return true;

        else
            return false;
    }
}