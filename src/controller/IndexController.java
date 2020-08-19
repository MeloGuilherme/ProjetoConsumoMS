package controller;

import application.Util;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import model.Pessoa;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ViewScoped
public class IndexController implements Serializable {

	private static final long serialVersionUID = -2701110827897445334L;

	private Pessoa pessoa;
	private String nome_arquivo;
	public static final String PATH_FILES = File.separator + "arquivos_gerados";
	public static final String PATH = PATH_FILES + File.separator + "arqs_gerados";

	public String validaCpfApi(String cpf) {

		try {

			Client c = Client.create();

			cpf = cpf.replaceAll(" ", "%20");

			WebResource wr = c.resource("http://192.168.10.20:8080/API-1.0-SNAPSHOT/valida_cpf/" + cpf);

			getPessoa().setCpf(wr.get(String.class));

			return cpf;
		}

		catch (Exception e){

			e.printStackTrace();
			Util.addMessageError("CPF inválido!.");

			return null;
		}

	}

	public void salvar(){

		try {

			getPessoa().setCpf(validaCpfApi(getPessoa().getCpf()));

			if(!getPessoa().getCpf().equals(null)){

				Util.addMessageInfo("CPF válido!");
				gerarArquivo();
			}

			else {

				Util.addMessageError("CPF Inválido!");
			}

		}

		catch (Exception e){

			e.printStackTrace();
			Util.addMessageError("CPF inválido!");
			return;
		}
	}

	public void gerarArquivo() throws IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String diretorio = System.getProperty("user.home") + PATH;

		// Criando os diretorios caso nao exista
		File file = new File(diretorio);

		if (!file.exists()) {
			file.mkdirs(); // mkdirs - cria arquivo ou diretorio (caso o diretorio anterior nao exista ele cria tambem)
		}

		File arquivo = new File(diretorio, getNome_arquivo() + ".txt");

		if(arquivo.exists()) {

			Util.addMessageError("Já existe um arquivo com este nome!");
			return;
		}

		// Nome do arquivo
		FileWriter arquivoFinal = new FileWriter(diretorio + File.separator + getNome_arquivo() + ".txt");
		PrintWriter gravarArq = new PrintWriter(arquivoFinal);

		// Informações para armazenar
		gravarArq.printf("Nome: " + getPessoa().getNome() + "%n");
		gravarArq.printf("CPF: " + getPessoa().getCpf() + "%n");
		gravarArq.printf("Idade: " + getPessoa().getIdade() + " anos" + "%n");
		gravarArq.printf("%n");
		gravarArq.printf("Data e Hora da geração do arquivo: " + sdf.format(new Date()));

		arquivoFinal.close();

		Util.addMessageInfo("Arquivo gerado com sucesso!");

		limpar();
	}

	public void limpar() {

		setPessoa(null);
	}

	public Pessoa getPessoa() {
		if (pessoa == null)
			pessoa = new Pessoa();
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public String getNome_arquivo() {
		return nome_arquivo;
	}

	public void setNome_arquivo(String nome_arquivo) {
		this.nome_arquivo = nome_arquivo;
	}

}
