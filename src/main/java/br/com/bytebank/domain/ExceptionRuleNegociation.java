package main.java.br.com.bytebank.domain;

public class ExceptionRuleNegociation extends RuntimeException{
   public ExceptionRuleNegociation(String mensagem)
   {
       super(mensagem);
   }
}
