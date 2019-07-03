package com.thoughtworks.aceleradora.servicos;

import com.thoughtworks.aceleradora.dominio.*;
import com.thoughtworks.aceleradora.repositorios.MinhaListaRepositorio;
import com.thoughtworks.aceleradora.repositorios.PedidoRepositorio;
import com.thoughtworks.aceleradora.repositorios.ProdutoProdutorRepositorio;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PedidoServico {

    private PedidoRepositorio repositorio;
    private ProdutoProdutorRepositorio produtoProdutorRepositorio;

    private ClienteServico clienteServico;

    public PedidoServico(PedidoRepositorio repositorio, ProdutoProdutorRepositorio produtoProdutorRepositorio, ClienteServico clienteServico) {
        this.repositorio = repositorio;
        this.produtoProdutorRepositorio = produtoProdutorRepositorio;
        this.clienteServico = clienteServico;
    }

    public Optional<Pedido> encontraUm(Long id) {
        Optional<Pedido> pedidos = repositorio.findById(id);
        return pedidos;
    }

    public List<Pedido> pegarPedidos() {
        return repositorio.findAll();
    }

    public void removerPedido(Long idCompra) {
        repositorio.deleteById(idCompra);
    }

    public Pedido salvarPedido(Pedido pedido) {

        pedido.setCliente(clienteServico.encontraCliente());
        pedido.setPedidosProdutosProdutores(pedido
                .getPedidosProdutosProdutores()
                .stream()
                .filter(pedidoProdutoProdutor -> pedidoProdutoProdutor.getProdutoProdutor() != null)
                .peek(pedidoProdutoProdutor -> pedidoProdutoProdutor.setValor(pedidoProdutoProdutor
                        .getProdutoProdutor().getPreco()))
                .collect(Collectors.toList())
        );

        return repositorio.save(pedido);
    }

    public Map<Produtor, List<ProdutoProdutor>> agrupaProdutosPorProdutor(Long idPedido) {
        Optional<Pedido> pedido = encontraUm(idPedido);

        List<PedidoProdutoProdutor> pedidosProdutosProdutoresDoPedido = pedido.get().getPedidosProdutosProdutores();

        List<ProdutoProdutor> produtoProdutor = new ArrayList<>();

        for (int i = 0; i < pedidosProdutosProdutoresDoPedido.size(); i++) {
            produtoProdutor.add(pedidosProdutosProdutoresDoPedido.get(i).getProdutoProdutor());
        }

        Map<Produtor, List<ProdutoProdutor>> byProdutor
                = produtoProdutor.stream()
                .collect(Collectors.groupingBy(ProdutoProdutor::getProdutor));
        return byProdutor;
    }

    public Map<Produto, List<ProdutoProdutor>> produtoresDoPedido(Long idPedido) {
        Optional<Pedido> pedido = encontraUm(idPedido);

        List<PedidoProdutoProdutor> pedidosProdutosProdutoresDoPedido = pedido.get().getPedidosProdutosProdutores();

        List<ProdutoProdutor> produtoProdutor = new ArrayList<>();

        List<Produto> produtos = new ArrayList<>();

        for (int i = 0; i < pedidosProdutosProdutoresDoPedido.size(); i++) {
            produtoProdutor.add(pedidosProdutosProdutoresDoPedido.get(i).getProdutoProdutor());
        }
        for (int i = 0; i < produtoProdutor.size(); i++) {
            produtos.add(produtoProdutor.get(i).getProduto());
        }

        Map<Produto, List<ProdutoProdutor>> byProduto
                = produtoProdutor.stream()
                .collect(Collectors.groupingBy(ProdutoProdutor::getProduto));
        return byProduto;
    }


    public Map<Produto, List<ProdutoProdutor>> agrupaProdutoresPorProdutos(Long idPedido) {
        Optional<Pedido> pedido = encontraUm(idPedido);

        List<PedidoProdutoProdutor> pedidoProdutoProdutoresDoPedido = pedido.get().getPedidosProdutosProdutores();

        List<ProdutoProdutor> produtoProdutores = new ArrayList<>();
        List<Produto> produtos = new ArrayList<>();

        for (int i = 0; i < pedidoProdutoProdutoresDoPedido.size(); i++) {
            produtos.add(pedidoProdutoProdutoresDoPedido.get(i).getProdutoProdutor().getProduto());
        }
        for (int i = 0; i <= produtoProdutorRepositorio.findAll().size(); i++) {
            for(int j = 0; j < produtos.size(); j++) {
                if(produtos.contains(produtoProdutorRepositorio.findByProdutoIn(produtos))) {
                    produtoProdutores.add(produtoProdutores.get(i));
                }
            }
        }
        Map<Produto, List<ProdutoProdutor>> byProduto
                = produtoProdutores
                .stream()
                .collect(Collectors.groupingBy(ProdutoProdutor::getProduto));
        return byProduto;
    }
}