

import org.junit.Assert;
import org.junit.Test;

import br.com.roberto.entidades.Usuario;

public class AssertTest {
	
	@Test
	public void test() {
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		
		Assert.assertEquals(1,1); //Iguais Para todos os tipos
		Assert.assertEquals(0.51,0.51, 0.01); //Em casos de Double tem que colocar o Delta, ou seja margem de erro
		Assert.assertEquals(0.51234,0.512, 0.001); //Em casos de Double tem que colocar o Delta, ou seja margem de erro
		Assert.assertEquals(Math.PI, 3.14, 0.01); //Exemplo de Pi com margem de erro de 0.01
		
		//Comparando Tipos Primitivos com Objetos
		//Comparando Tipos Numéricos
		//JUnit não faz unboxing autboxing de objetos
		int i =5;
		Integer i2 = 5;
		Assert.assertEquals(Integer.valueOf(i), i2); //TipoPrimitivo para Objeto
		Assert.assertEquals(i, i2.intValue());//Objeto para Tipo Primitivo
		
		//Comparações de String
		Assert.assertEquals("bola", "bola");
		Assert.assertTrue("bola".equalsIgnoreCase("Bola")); //Comparando o Exemplo acima
		Assert.assertTrue("bola".startsWith("bo")); //Comparando com o radical
		
		//Comparando Objetos
		Usuario u1 = new Usuario("Usuario 1");
		Usuario u2 = new Usuario("Usuario 1");
		Assert.assertEquals(u1, u2); //Cuidado .... Deve-se usar o Equals e o HashCode
		
		Usuario u3 = u2;
		Assert.assertSame(u3, u2); //Realizando a Comparação das duas Instâncias
		
		//Assert.assertTrue(u3==null); //Testa se o valor é nulo
		//Assert.assertNull(u3);

		//Todas essas possibilidades tem a sua negação
//		Assert.assertNotEquals(unexpected, actual);
//		Assert.assertNotSame(unexpected, actual);
//		Assert.assertNotNull(object);
		
		//Assert.assertEquals("Erro de Comparação", 1,2); //Em caso de Erro Mostra a Mensagem Erro de Comparação
		//Cuidado ao Ler os parâmetros do assertEquals , veja o esperado e o atual
		
	}
	

}

