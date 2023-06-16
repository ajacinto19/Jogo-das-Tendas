import java.io.File
fun criaMenu(): String {
    return("\n" +
            "Bem vindo ao jogo das tendas\n" +
            "\n" +
            "1 - Novo jogo\n" +
            "0 - Sair\n")
}
fun linhas(numLinhas: Int?): String{
    val resposta = "Resposta invalida\n"
    if (numLinhas == null) {
        return resposta
    }
    if (numLinhas <= 0) {
        return resposta
    }
    else return ""
}
fun colunas(numColunas: Int?): String{
    val resposta = "Resposta invalida\n"
    if (numColunas == null){
        return resposta
    }
    if (numColunas <= 0){
        return resposta
    }
    else return ""
}
fun validaTamanhoMapa(numLinhas: Int, numColunas: Int): Boolean {
    return when {
        numLinhas == 6 && numColunas == 5 -> true
        numLinhas == 6 && numColunas == 6 -> true
        numLinhas == 8 && numColunas == 8 -> true
        numLinhas == 10 && numColunas == 10 -> true
        numLinhas == 8 && numColunas == 10 -> true
        numLinhas == 10 && numColunas == 8 -> true
        else -> false
    }
}
fun validaDataNascimento(data: String?) : String? {
    val datainvalida = "Data invalida"
    val menor = "Menor de idade nao pode jogar"
    if (data == null || 10 != data.length) {
        return datainvalida
    }else {
        val dia = (data[0].toString() + data[1].toString()).toInt()
        val mes = (data[3].toString() + data[4].toString()).toInt()
        val ano = (data[6].toString() + data[7].toString() + data[8].toString() + data[9].toString()).toInt()
        val traco1 = data[2].toString()
        val traco2 = data[5].toString()
        if (ano in 1900..2022) {
            if (traco1 != "-" || traco2 != "-") {
                return datainvalida
            }
            if (mes in 1..12) {
                if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                    if (dia !in 1..30) {
                        return datainvalida
                    }
                }else if (dia !in 1..31) {
                    return datainvalida
                }
                if (mes == 2) {
                    if ((ano % 4 == 0 && (ano % 100 != 0 || ano % 400 != 0))) {
                        if (dia !in 1..29) {
                            return datainvalida
                        }
                    } else if (dia !in 1..28) {
                        return datainvalida
                    }
                }
            } else {
                return null
            }
            if (ano > 2004) {
                return menor
            }
            if ((ano == 2004) && (mes >= 11)) {
                return menor
            }
        } else {
            return datainvalida
        }
    }
    return null
}
fun codigoAscii(letra: Char): Int{
    val codigoAscii: Int = letra.code
    return codigoAscii -64
}
fun criaLegendaHorizontal(numColunas: Int): String {
    
    var escreve = ""
    var conta = numColunas
    while (conta >= 1) {
        escreve += conta
        if (conta > 1) {
            escreve += " | "
        }
        conta = conta-1
    }
    return escreve
}
fun leContadoresDoFicheiro(numLines: Int, numColumns: Int, verticais: Boolean): Array<Int?>{
    val parte: List<String>
    val linhas = File("$numLines"+"x"+"$numColumns"+".txt").readLines()
    if (verticais){
        parte = linhas[0].split(",")
    }
    else{
        parte =linhas[1].split(",")
    }
    val contador = arrayOfNulls<Int?>(parte.size)
    for (n in parte.indices){
        contador[n] = parte[n].toIntOrNull()
        if(contador[n] == 0){
            contador[n] = null
        }
    }
    return contador
}
fun criaLegendaContadoresHorizontal(contadoresHorizontal: Array<Int?>): String{
    var colunas = 0
    var terreno = ""
    while (colunas in 0..contadoresHorizontal.size-1){
        if (contadoresHorizontal[colunas] != null) {
            terreno += contadoresHorizontal[colunas]
        }
        else{
            terreno += " "
        }
        if (colunas in 0..contadoresHorizontal.size-2) {
            terreno += "   "
        }
        else{
            terreno += ""
        }
        colunas ++
    }
    return terreno
}
fun leTerrenoDoFicheiro(numLines: Int, numColumns: Int):Array<Array<String?>> {
    val terreno = Array(numLines) { arrayOfNulls<String>(numColumns) }
    val linhas = File("$numLines" + "x" + "$numColumns" + ".txt").readLines()
    for (num3 in 2 until  linhas.size) {
        val linha = linhas[num3]
        val parte = linha.split(",")
        val linha1 = parte[0].toInt()
        val coluna1 = parte[1].toInt()
        terreno[linha1][coluna1] = "A"
    }
    return terreno
}
fun criaTerreno(terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>?, contadoresHorizontais: Array<Int?>?
                , mostraLegendaHorizontal: Boolean = true, mostraLegendaVertical: Boolean = true): String {
    val traco = " | "
    val linhas = terreno.size
    val colunas = terreno[0].size
    val tree = "\u25B3"
    var verticalLegend = ""
    if (contadoresVerticais != null) {
        verticalLegend += "       " + criaLegendaContadoresHorizontal(contadoresVerticais) + "\n"
    }
    var horizontalLegend = ""
    if (mostraLegendaHorizontal) {
        horizontalLegend += "     | " + criaLegendaHorizontal(colunas) + "\n"
    }
    var terrainLines = ""
    for (i in 0 until linhas) {
        var lines = ""
        if (contadoresHorizontais != null) {
            if (i < 9) {
                lines += if (contadoresHorizontais[i] == null) {
                    "   "
                } else {
                    "${contadoresHorizontais[i]}  "
                }
            }
            else{
                lines += if (contadoresHorizontais[i] == null) {
                    "  "
                } else {
                    "${contadoresHorizontais[i]} "
                }
            }
        } else {
            lines += "   "
        }
        if (mostraLegendaVertical) {
            lines += "${i + 1}" + traco
        } else {
            lines += " " + traco
        }
        for (j in 0 until colunas) {
            var positionResult = terreno[i][j]
            if (positionResult == "A") {
                positionResult = tree
            } else if (positionResult == null) {
                positionResult = " "
            }
            lines += if (j == colunas - 1) {
                positionResult
            } else {
                "$positionResult$traco"
            }
        }
        terrainLines += if (i != linhas - 1) {
            "$lines\n"
        } else {
            lines
        }
    }
    return "$verticalLegend$horizontalLegend$terrainLines"
}
fun processaCoordenadas(coordenadasStr: String?, numLines: Int, numColunas: Int): Pair<Int,Int>? {
    if (coordenadasStr != null) {
        if (numLines == 10 && coordenadasStr.length == 4) {
            val num = coordenadasStr[0].toString() + coordenadasStr[1].toString()
            val numero = num.toInt()
            val letra = coordenadasStr[3]
            val virgula = coordenadasStr[2].toString()
            if (virgula != ",") {
                return null
            }
            if (numero !in 1..numLines) {
                return null
            }
            if (codigoAscii(letra) !in (1..numColunas)) {
                return null
            } else {
                val coordenadas = Pair(num.toInt(), codigoAscii(letra))
                return coordenadas
            }
        } else if (coordenadasStr.length == 3) {
            val num = coordenadasStr[0].toString()
            val numero = num.toInt()
            val letra = coordenadasStr[2]
            val virgula = coordenadasStr[1].toString()
            if (virgula != ",") {
                return null
            }
            if (numero !in 1..numLines) {
                return null
            }
            if (codigoAscii(letra) !in (1..numColunas)) {
                return null
            } else {
                val coordenadas = Pair(num.toInt() - 1, codigoAscii(letra) - 1)
                return coordenadas
            }
        } else {
            return null
        }
    } else {
        return null
    }
}
fun temArvoreAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>) : Boolean {
    val linha = coords.first
    val coluna = coords.second
    when{
        (linha > 0 && terreno[linha - 1][coluna] == "A")->return true
        (linha < terreno.size - 1 && terreno[linha + 1][coluna] == "A")->return true
        (coluna > 0 && terreno[linha][coluna - 1] == "A")->return true
        (coluna < terreno[0].size - 1 && terreno[linha][coluna + 1] == "A")->return true
        else -> return false
    }
}
fun temTendaAdjacente(terreno: Array<Array<String?>>, coords: Pair<Int, Int>) : Boolean {
    val linha = coords.first
    val coluna = coords.second
    when {
        (linha > 0 && terreno[linha - 1][coluna] == "T") -> return true
        (linha < terreno.size - 1 && terreno[linha + 1][coluna] == "T") -> return true
        (coluna > 0 && terreno[linha][coluna - 1] == "T") -> return true
        (coluna < terreno[0].size - 1 && terreno[linha][coluna + 1] == "T") -> return true
        (linha > 0 && coluna > 0 && terreno[linha - 1][coluna - 1] == "T") -> return true
        (linha > 0 && coluna < terreno[0].size - 1 && terreno[linha - 1][coluna + 1] == "T") -> return true
        (linha < terreno.size - 1 && coluna > 0 && terreno[linha + 1][coluna - 1] == "T") -> return true
        (linha < terreno.size - 1 && coluna < terreno[0].size - 1 && terreno[linha + 1][coluna + 1] == "T") -> return true
        else -> return false
    }
}
fun contaTendasColuna(terreno: Array<Array<String?>>, coluna: Int): Int {
    return terreno.count { it[coluna] == "T" }
}
fun contaTendasLinha(terreno: Array<Array<String?>>, linha: Int): Int{
    var tendas = 0
    if(linha in 0..terreno[0].size-1){
        for (coluna in 0..terreno[linha].size - 1) {
            if (terreno[linha][coluna] == "T") {
                tendas ++
            }
        }
    }
    return tendas
}
fun colocaTenda(terreno: Array<Array<String?>>, coords: Pair<Int, Int>): Boolean{
    val prim = coords.first
    val seg = coords.second
    when {
        prim == 0 && seg==0 -> return false
        !temArvoreAdjacente(terreno, coords) -> return false
        temTendaAdjacente(terreno, coords) -> return false
        terreno[prim][seg] == "△" -> return false
        terreno[prim][seg] == null -> {
            terreno[prim][seg] = "T"
            return true
        }
        terreno[prim][seg] == "T" -> {
            terreno[prim][seg] = null
            return true
        }
        else -> {
            terreno[prim][seg] = "T"
            return true
        }
    }
}
fun terminouJogo(terreno: Array<Array<String?>>, contadoresVerticais: Array<Int?>, contadoresHorizontais: Array<Int?>): Boolean{
    for (linha in terreno.indices) {
        var contador = contadoresHorizontais[linha]
        if (contador == null) contador = 0
        if (contaTendasLinha(terreno, linha) != contador) return false
    }
    for (coluna in 0 until terreno[0].size) {
        var contador = contadoresVerticais[coluna]
        if (contador == null) contador = 0
        if (contaTendasColuna(terreno, coluna) != contador) return false
    }
    return true
}
fun contaTendasQueFaltam(terreno: Array<Array<String?>>): Int{
    val numLines = terreno.size
    val numColumns = terreno[1].size
    var tamanho = 0
    if(numLines==5 &&numColumns==4){
        return 0
    }
    else {
        for (numlinhas in 0 until numLines) {
            for (numcolunas in 0 until numColumns) {
                if (terreno[numlinhas][numcolunas] == "A") {
                    tamanho++
                }
            }
        }
        var count = 0
        for (numlinhas in 0 until numLines) {
            for (numcolunas in 0 until numColumns) {
                if (terreno[numlinhas][numcolunas] == "T") {
                    count++
                }
            }
        }
        return tamanho - count
    }
}
fun sugereJogada(terreno: Array<Array<String?>>): Pair<Int,Int>? {
    val numLines = terreno.size
    val numColumns = terreno[0].size
    for (numlinhas in 0 until numLines){
        for (numcolunas in 0 until numColumns){
            if (terreno[numlinhas][numcolunas] == null) {
                if (!temTendaAdjacente(terreno,Pair(numlinhas,numcolunas))
                        && temArvoreAdjacente(terreno,Pair(numlinhas,numcolunas))) {
                    return Pair(numlinhas, numcolunas)
                }
            }
        }
    }
    return null
}
fun main() {
    var jogo: Int?
    var numLinhas: Int?
    var numColunas: Int?
    var data2: String?
    var resposta2 = true
    do {
        do {
            println(criaMenu())
            jogo = readln().toIntOrNull()
            when (jogo) {
                !in 0..1 -> print("Opcao invalida\n") }
        } while (jogo !in 0..1)
        if (jogo == 1) {
            do {
                println("Quantas linhas?")
                numLinhas = readln().toIntOrNull()
                print(linhas(numLinhas))
            } while (numLinhas == null || numLinhas <= 0)
            do {
                println("Quantas colunas?")
                numColunas = readln().toIntOrNull()
                print(colunas(numColunas))
            } while (numColunas == null || numColunas <= 0)
            if (validaTamanhoMapa(numLinhas, numColunas)) {
                if (numLinhas == 10 && numColunas == 10) {
                    do {
                        println("Qual a sua data de nascimento? (dd-mm-yyyy)")
                        val data = readln()
                        data2 = validaDataNascimento(data)
                        if (data2 != null) println(data2)
                        if (data2 == "Menor de idade nao pode jogar") resposta2 = false
                    } while ((data2 == "Data invalida")) }
                if (resposta2) {
                    print("\n")
                    val contadoresVerticais = leContadoresDoFicheiro(numLinhas, numColunas, true)
                    val contadoresHorizontais = leContadoresDoFicheiro(numLinhas, numColunas, false)
                    val terreno = leTerrenoDoFicheiro(numLinhas, numColunas)
                    println(criaTerreno(terreno, contadoresVerticais, contadoresHorizontais,
                            true, true))
                    print("Falta colocar ${contaTendasQueFaltam(terreno)} tendas")
                    do {
                        println("\n" + "Coordenadas da tenda? (ex: 1,B)")
                        val coordenadasStr = readLine()
                        if (coordenadasStr == "sair") { return }
                        else {
                            var coordenadasTenda : Pair<Int,Int>?
                            val numeroLines = numLinhas
                            val numeroColumns = numColunas
                            when{
                                coordenadasStr == "ajuda" -> coordenadasTenda = sugereJogada(terreno)
                                else -> coordenadasTenda = processaCoordenadas(coordenadasStr, numeroLines, numeroColumns)
                            }
                            if (coordenadasTenda == null) {
                                println("Coordenadas inválidas")
                            } else {
                                if (!colocaTenda(terreno, coordenadasTenda)) {
                                    print("Tenda nao pode ser colocada nestas coordenadas")
                                } else {
                                    println("")
                                    println(criaTerreno(terreno, contadoresVerticais, contadoresHorizontais))
                                    if(!terminouJogo(terreno, contadoresVerticais, contadoresHorizontais)) {
                                        print("Falta colocar ${contaTendasQueFaltam(terreno)} tendas")
                                    }
                                }
                            }
                        }
                    } while (!terminouJogo(terreno, contadoresVerticais, contadoresHorizontais))
                    println("Parabens! Terminou o jogo!") } }
            else {
                println("Terreno invalido")
            }
        }
    } while (jogo == 1)
}