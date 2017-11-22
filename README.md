# 
**Cassebot**
###
Toda empresa possui um funcionário que esta por dentro de todos os assuntos da empresa, ele é sempre requisitado pelos demais quando alguêm precisa de alguma informação.

Imagine um "funcionário" que ficasse disponível 24 horas por dias, 7 dias por semana respondendo todos os funcionários ao mesmo tempo!

Esta é a proposta do CasseBot, ser um ERP em forma de ChatBot para lançamentos de cadastros de clientes, produtos, vendas, estoque etc... centralizando todas as informações de forma que a consulta das mesmas fica facilitada.

Faça um teste você mesmo, entre no seu telegram e procure pelo usuário @SimNetworkBot e experimente a opção "Demonstração".

####
Este repositório é um demonstrativo do ChatBot que esta sendo desenvolvido pela Iniciativa Sim Network como forma de apresentação do conhecimento utilizado.

Está sendo utilizado as seguintes tecnologias:
- Linguagem Java
- Framework Spring Boot, Lombok e Pengrad
- Dependências Gradle

# 
**Como funciona?**
###### 
Esta aplicação tem como objetivo principal ser um ERP para uso empresarial, onde será possível cadastrar clientes, produtos, gerar relatórios, de forma mais facilitada utilizando Telegram como ferramenta de interação.

Esta aplicação foi desenvolvida seguindo um conceito próprio de Diálogos, Passos e Entidade, todos estes detalhes podem ser customizados em um arquivo xml, sem a necessidade de acesso ao fonte principal ou ao banco de dados.
#

**É possível configurar tudo na aplicação pelo XML, tanto tabelas quanto o comportamento do bot.** 

##
**Passo**
###### 
É uma ação realizada pelo bot podendo ser desde uma mensagem simples, até uma inserção de um registro no banco:
```
<bean parent="|S|SIMPLEMESSAGE|">
  <property name="botMessage" value="Hello World" />
</bean>
```
Exemplo de Mensagem simples

```
<bean parent="|S|REQUESTCONFIRMATION|" />
```
Exemplo de confirmação de dados (O sistema pega tudo que o usuário inseriu anteriormente e pergunta a ele se esta correto).

##
**Diálogo**
######
É um conjunto ordenado de passos, um diálogo pode começar mandando uma mensagem ao usuário, pedir uma informação, confirmar os dados e inserir algo no banco.
```
<bean id="|D|Inserir Sugestões|" parent="Dialog">
		<property name="nomeSchema" value="|D|Inserir Sugestões|" ></property>
		<property name="noPermissionRequired" value="true"/>
		<property name="steps">
			<map>
				<entry key="1">
					<bean parent="|S|REQUESTSTRING|">
						<property name="entity" value="Sugestões" />
						<property name="key" value="user:Título" />
					</bean>
				</entry>
				<entry key="2">
					<bean parent="|S|REQUESTSTRING|">
						<property name="entity" value="Sugestões" />
						<property name="key" value="user:Descrição" />
					</bean>
				</entry>
				<entry key="3">
					<bean parent="|S|REQUESTCONFIRMATION|" />
				</entry>
				<entry key="4">
					<bean parent="|S|INSERT|">
						<property name="entity" value="Sugestões" />
					</bean>
				</entry>
			</map>
		</property>
	</bean>
  ```
  Exemplo acima de um diálogo completo de inserção de uma sugestão para bot, onde é solicitado informações ao usuário e após sua confirmação final é inserido no banco.
  
##
**Entidade**
######
É um padrão de registro para ser salvo no banco. Uma entidade é dinâmica, e sua estrutura tambem é definida por xml sem a necessidade de criar novas tabelas ou alterar as já existentes:
```
<bean id="Sugestões" parent="Entity">
		<property name="entityName" value="Sugestões" />
	</bean>
	<bean id="|E|001|" parent="Sugestões">
		<property name="order" value="1" />
		<property name="fieldName" value="Título" />
		<property name="type" value="STRING" />
		<property name="isKey" value="true" />
	</bean>
	<bean id="|E|002|" parent="Sugestões">
		<property name="order" value="2" />
		<property name="fieldName" value="Descrição" />
		<property name="type" value="STRING" />
	</bean>
	<bean id="|E|003|" parent="Sugestões">
		<property name="order" value="3" />
		<property name="fieldName" value="Realizado" />
		<property name="type" value="BOOLEAN" />
		<property name="defaultValue" value="False" />
	</bean>
  ```
  Exemplo acima de uma entidade chamada Sugestões, ela tem 3 campos (Título, Descrição e Realizado respectivamente) onde a chave é o título e Realizado por default é False.
  
#
**Conclusão**
######
O fonte desta aplicação é somente uma demonstração da versão oficial que esta sendo continuada em outro repositório privado, com ela é possível ter uma noção da potencia da solução com alguns exemplos de possibilidades dentro destes diálogos do nosso ChatBot.

Qualquer dúvida pode me contatar pelo (12) 99664-9876 ou peli e-mail felipe.casseb@gmail.com 

