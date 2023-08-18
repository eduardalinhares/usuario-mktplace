package br.com.senai.usuariosmktplace;

import br.com.senai.usuariosmktplace.core.dao.DaoUsuario;
import br.com.senai.usuariosmktplace.core.dao.FactoryDao;
import br.com.senai.usuariosmktplace.core.dao.ManagerDb;
import br.com.senai.usuariosmktplace.core.domain.Usuario;

public class initApp {
	
	public static void main(String [] args) {
		DaoUsuario dao = FactoryDao.getInstance().getDaoUsuario();
		Usuario usuario = dao.buscarPor("asd");
		
		if(usuario == null) {
			System.out.println("Nao existe usuario");
			
		}else {
			System.out.println(usuario.getNomeCompleto());
		}
	}
}
