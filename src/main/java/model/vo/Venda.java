package model.vo;

import java.sql.Date;
import java.util.ArrayList;

public class Venda {

	private int idVenda;
	private Double valor;
	private ArrayList<ItemRemedio> itensRemedios;
	private ArrayList<ItemProduto> itensProdutos;
	private Date dataVenda;
	private FormaPagamento formaPagamento;
	private Usuario usuario;

	public int getIdVenda() {
		return idVenda;
	}

	public void setIdVenda(int idVenda) {
		this.idVenda = idVenda;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public ArrayList<ItemRemedio> getItensRemedios() {
		return itensRemedios;
	}

	public void setItensRemedios(ArrayList<ItemRemedio> itensRemedios) {
		this.itensRemedios = itensRemedios;
	}

	public ArrayList<ItemProduto> getItensProdutos() {
		return itensProdutos;
	}

	public void setItensProdutos(ArrayList<ItemProduto> itensProdutos) {
		this.itensProdutos = itensProdutos;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Venda(int idVenda, Double valor, ArrayList<ItemRemedio> itensRemedios, ArrayList<ItemProduto> itensProdutos,
			Date dataVenda, FormaPagamento formaPagamento, Usuario usuario) {
		super();
		this.idVenda = idVenda;
		this.valor = valor;
		this.itensRemedios = itensRemedios;
		this.itensProdutos = itensProdutos;
		this.dataVenda = dataVenda;
		this.formaPagamento = formaPagamento;
		this.usuario = usuario;
	}

	public Venda() {
		super();
		// TODO Auto-generated constructor stub
	}

}