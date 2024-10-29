<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%-- a linha abaixo faz a inclusão de um outro arquivo no arquivo atual --%>
<c:import url="topo.jsp" />

<div class="alert-success text-center espaco">
    Cadastrar Usuário
</div>

<form name="cadastrar" method="post" action="Inserir">
    <div class="row">
        <div class="col-md-5 mb-3">
            <label>RA</label>
            <input type="text" class="form-control" name="ra" required value="${ra != null ? ra : ''}" />
            <span class="erro">${ra_msg}</span>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5 mb-3">
            <label>NOME</label>
            <input type="text" class="form-control" name="nome" value="${nome != null ? nome : ''}"/>
            <span class="erro">${nome_msg}</span>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5 mb-3">
            <label>CURSO</label>
            <input type="text" class="form-control" name="curso" value="${curso != null ? curso : ''}"/>
            <span class="erro">${curso_msg}</span>
        </div>
    </div>
    <div class="row">
        <div class="col-md-5 mb-3">
            <input type="hidden" name="operacao" value="Inserir" />
            <input
                type="submit"
                class="form-control btn btn-primary"
                name="bt_enviar"
                value="Cadastrar"/>
        </div>
    </div>    
</form>

<c:import url="rodape.jsp" />