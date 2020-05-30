package model.bo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import model.dao.ProdutoDAO;
import model.dao.RemedioDAO;
import model.dao.VendaDAO;
import model.vo.Produto;
import model.vo.Remedio;
import model.vo.Venda;

public class GeradorDePlanilha {

	public String extensao;
	public XSSFWorkbook planilha;
	public String mensagem = "";

	public String gerarPlanilhaRemedio(List<Remedio> remedios, String caminho) {
		XSSFWorkbook planilha = null;
		OutputStream outputStream = null;
		try {
			planilha = new XSSFWorkbook();
			DateFormat data = new SimpleDateFormat("dd/MM/yyyy");

			XSSFSheet sheet = planilha.createSheet("Remédio");
			//
			XSSFRow linhaHeader = sheet.createRow(0);
			linhaHeader.createCell(0).setCellValue("Código de barra");
			linhaHeader.createCell(1).setCellValue("Dosagem");
			linhaHeader.createCell(2).setCellValue("Composição");
			linhaHeader.createCell(3).setCellValue("Genérico");
			linhaHeader.createCell(4).setCellValue("Nome");
			linhaHeader.createCell(6).setCellValue("Preço");
			linhaHeader.createCell(7).setCellValue("Estoque");
			linhaHeader.createCell(8).setCellValue("Forma de uso");
			linhaHeader.createCell(9).setCellValue("Laboratorio");
			linhaHeader.createCell(10).setCellValue("Data");

			int count = 1;
			for (Remedio remedio : remedios) {
				XSSFRow novaLinha = sheet.createRow(count);
				novaLinha.createCell(0).setCellValue(remedio.getCodBarra());
				novaLinha.createCell(1).setCellValue(remedio.getDosagem());
				novaLinha.createCell(2).setCellValue(remedio.getComposicao());
				String isGenerico = "";
				if (remedio.isGenerico()) {
					isGenerico = "Sim";
				} else {
					isGenerico = "Não";
				}
				novaLinha.createCell(3).setCellValue(isGenerico);
				novaLinha.createCell(4).setCellValue(remedio.getNome());
				novaLinha.createCell(6).setCellValue("R$" + remedio.getPrecoVenda());
				novaLinha.createCell(7).setCellValue(remedio.getEstoque());
				novaLinha.createCell(8).setCellValue(remedio.getFormaUso().getDescricao());
				novaLinha.createCell(9).setCellValue(remedio.getLaboratorio().getNomeLaboratorio());
				novaLinha.createCell(10).setCellValue(data.format(remedio.getDataCadastro()));
				count++;
			}

			outputStream = new FileOutputStream(caminho);
			planilha.write(outputStream);
			mensagem = "Planilha criada com sucesso";
			return mensagem;
		} catch (FileNotFoundException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} catch (IOException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} finally {
			if (planilha != null) {
				try {
					planilha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		RemedioDAO remedioDAO = new RemedioDAO();
		List<Remedio> remedios = remedioDAO.listarComSeletor(null);
		new GeradorDePlanilha().gerarPlanilhaRemedio(remedios, "");
	}

	public String gerarPlanilhaProduto(List<Produto> produtos, String caminho) {
		XSSFWorkbook planilha = null;
		OutputStream outputStream = null;
		try {
			planilha = new XSSFWorkbook();
			DateFormat data = new SimpleDateFormat("dd/MM/yyyy");

			XSSFSheet sheet = planilha.createSheet("Produto");

			XSSFRow linhaHeader = sheet.createRow(0);
			linhaHeader.createCell(0).setCellValue("Código de barra");
			linhaHeader.createCell(1).setCellValue("Nome");
			linhaHeader.createCell(2).setCellValue("Preço");
			linhaHeader.createCell(3).setCellValue("Categoria");
			linhaHeader.createCell(4).setCellValue("Estoque");
			linhaHeader.createCell(5).setCellValue("Data");

			int count = 1;
			for (Produto produto : produtos) {
				XSSFRow novaLinha = sheet.createRow(count);
				novaLinha.createCell(0).setCellValue(produto.getCodBarra());
				novaLinha.createCell(1).setCellValue(produto.getNome());
				novaLinha.createCell(2).setCellValue("R$" + produto.getPrecoVenda());
				novaLinha.createCell(3).setCellValue(produto.getCategoria().getNomeCategoria());
				novaLinha.createCell(4).setCellValue(produto.getEstoque());
				novaLinha.createCell(5).setCellValue(data.format(produto.getDataCadastro()));
				count++;
			}

			outputStream = new FileOutputStream(caminho);
			planilha.write(outputStream);
			mensagem = "Planilha criada com sucesso";
			return mensagem;
		} catch (FileNotFoundException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} catch (IOException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} finally {
			if (planilha != null) {
				try {
					planilha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main1(String[] args) {
		ProdutoDAO produtoDAO = new ProdutoDAO();
		List<Produto> produtos = produtoDAO.listarComSeletor(null);
		new GeradorDePlanilha().gerarPlanilhaProduto(produtos, "");
	}

	public String gerarPlanilhaVenda(List<Venda> vendas, String caminho) {
		XSSFWorkbook planilha = null;
		OutputStream outputStream = null;
		try {
			planilha = new XSSFWorkbook();

			DateFormat data = new SimpleDateFormat("dd/MM/yyyy");

			XSSFSheet sheet = planilha.createSheet("Venda");

			XSSFRow linhaHeader = sheet.createRow(0);
			linhaHeader.createCell(0).setCellValue("Id");
			linhaHeader.createCell(1).setCellValue("Valor");
			linhaHeader.createCell(2).setCellValue("Data da venda");
			int count = 1;
			for (Venda venda : vendas) {
				XSSFRow novaLinha = sheet.createRow(count);
				novaLinha.createCell(0).setCellValue(venda.getIdVenda());
				novaLinha.createCell(1).setCellValue("R$" + venda.getValor());
				novaLinha.createCell(2).setCellValue(data.format(venda.getDataVenda()));
				count++;
			}

			outputStream = new FileOutputStream(caminho);
			planilha.write(outputStream);
			mensagem = "Planilha criada com sucesso";
			return mensagem;
		} catch (FileNotFoundException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} catch (IOException e) {
			mensagem = "ERRO ao salvar planilha no: ";
			return mensagem + caminho;
		} finally {
			if (planilha != null) {
				try {
					planilha.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main2(String[] args) {
		VendaDAO vendaDAO = new VendaDAO();
		List<Venda> vendas = (List<Venda>) vendaDAO;
		new GeradorDePlanilha().gerarPlanilhaVenda(vendas, "");
	}

}
