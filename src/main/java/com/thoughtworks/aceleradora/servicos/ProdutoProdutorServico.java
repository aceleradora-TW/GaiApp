package com.thoughtworks.aceleradora.servicos;

import com.thoughtworks.aceleradora.dominio.*;
import com.thoughtworks.aceleradora.repositorios.MinhaListaRepositorio;
import com.thoughtworks.aceleradora.repositorios.ProdutoProdutorRepositorio;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProdutoProdutorServico {
    private ProdutoProdutorRepositorio produtoProdutorRepositorio;
    private PedidoServico pedidoServico;
    private MinhaListaRepositorio minhaListaRepositorio;


    public ProdutoProdutorServico(ProdutoProdutorRepositorio produtoProdutorRepositorio, PedidoServico pedidoServico, MinhaListaRepositorio minhaListaRepositorio) {
        this.produtoProdutorRepositorio = produtoProdutorRepositorio;
        this.pedidoServico = pedidoServico;
        this.minhaListaRepositorio = minhaListaRepositorio;
    }

    public List<ProdutoProdutor> pegarTodosProdutosProdutores (){
        return produtoProdutorRepositorio.findAll();
    }


    public Map<Produto, List<ProdutoProdutor>> organizarProdutosProdutoresDaListadoCliente (List<ProdutoProdutor> produtosProdutores, Long idLista){

        Optional<MinhaLista> lista = minhaListaRepositorio.findById(idLista);
        List<Produto> produtos = lista.get().getProdutos();

        List<ProdutoProdutor> produtosProdutoresDaLista = produtoProdutorRepositorio.findByProdutoIn(produtos);

        Map<Produto, List<ProdutoProdutor>> byProdProd
                = produtosProdutoresDaLista.stream()
                .collect(Collectors.groupingBy(ProdutoProdutor::getProduto));

        return byProdProd;
    }


    public List<ProdutoresDeProdutos> organizarProdutoresDeProdutos(List<ProdutoProdutor> produtoProdutores) {
        Map<Produto, ProdutoresDeProdutos> mapaDeProdutoresDeProdutos = new HashMap<>();
        for (ProdutoProdutor produtoProdutor : produtoProdutores) {
            Produto produto = produtoProdutor.getProduto();
            Produtor produtor = produtoProdutor.getProdutor();
            if(mapaDeProdutoresDeProdutos.containsKey(produto)) {
                ProdutoresDeProdutos produtoresDeProdutos = mapaDeProdutoresDeProdutos.get(produto);
                produtoresDeProdutos.adicionaProdutor(produtor);
            } else {
                ProdutoresDeProdutos produtorDeProduto =
                        new ProdutoresDeProdutos(produto, produtor);

                mapaDeProdutoresDeProdutos.put(produto, produtorDeProduto);
            }
        }
        return new ArrayList<>(mapaDeProdutoresDeProdutos.values());
    }


}