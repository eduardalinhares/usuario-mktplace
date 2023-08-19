package br.com.senai.usuariosmktplace;

import java.security.Provider.Service;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.dao.ManagerDb;
import br.com.senai.usuariosmktplace.core.domain.Usuario;
import br.com.senai.usuariosmktplace.core.service.UsuarioService;

public class InitApp {
	
	public static void main(String [] args) {
		
		UsuarioService serice = new UsuarioService();
		
		System.out.println(serice.removerAcentoDo("Jacó da Silva") );
		System.out.println(serice.fracionar("José da Silva Alburquerque dos Anjos e Bragaça"));
		System.out.println(serice.gerarLoginPor("José da silva dos Anjos"));
		System.out.println(serice.gerarHashDa("jose123456"));
	}
}
