import { useEffect, useState } from 'react';
import { View, Text, FlatList, TouchableOpacity, StyleSheet } from 'react-native';
import { listarFuncionarios } from '@/services/funcionarioService';
import { useRouter } from 'expo-router';

export default function TelaFuncionarios() {
    const router = useRouter();
    const [funcionarios, setFuncionarios] = useState([]);

    useEffect(() => {
        carregarDados();
    }, []);

    async function carregarDados(idEspecialidade?: number) {
        try {
            const dados = await listarFuncionarios(idEspecialidade);
            console.log("O QUE VEIO DO JAVA:", dados);
            setFuncionarios(dados);
        } catch (error) {
            console.error("Erro ao carregar dados", error);
        }
    }

    return (
            <View style={styles.container}>
                {/* ... seus botões de filtro ... */}

                <FlatList
                    data={funcionarios}
                    keyExtractor={(item) => item.id.toString()}
                    renderItem={({ item }) => (
                        // 3. O SEGREDO ESTÁ AQUI: O TouchableOpacity deve envolver o conteúdo
                        <TouchableOpacity
                            style={styles.card}
                            onPress={() => {
                                console.log("Clicou no ID:", item.id); // Para você ver no terminal
                                router.push(`/detalhes/${item.id}`);
                            }}
                        >
                            <Text style={styles.name}>{item.nome}</Text>
                            <Text style={styles.email}>{item.email}</Text>
                            <Text style={{ color: '#3498db', marginTop: 5 }}>Ver perfil →</Text>
                        </TouchableOpacity>
                    )}
                />
            </View>
        );
    }

const styles = StyleSheet.create({
    container: {
        flex: 1,
        paddingTop: 60,
        paddingHorizontal: 20,
        backgroundColor: '#f5f5f5'
    },
    title: {
        fontSize: 26,
        fontWeight: 'bold',
        color: '#333',
        marginBottom: 10
    },
    filterContainer: {
        flexDirection: 'row',
        gap: 10,
        marginBottom: 20
    },
    button: {
        backgroundColor: '#3498db',
        paddingVertical: 10,
        paddingHorizontal: 20,
        borderRadius: 25
    },
    buttonText: {
        color: '#fff',
        fontWeight: 'bold'
    },
    listContent: {
        paddingBottom: 40
    },
    card: {
        padding: 20,
        backgroundColor: '#fff',
        borderRadius: 15,
        marginBottom: 12,
        // Sombra para Android
        elevation: 3,
        // Sombra para iOS
        shadowColor: '#000',
        shadowOffset: { width: 0, height: 2 },
        shadowOpacity: 0.1,
        shadowRadius: 4,
    },
    name: {
        fontSize: 18,
        fontWeight: 'bold',
        color: '#2c3e50'
    },
    email: {
        fontSize: 14,
        color: '#7f8c8d',
        marginTop: 4
    },
    moreInfo: {
        fontSize: 12,
        color: '#3498db',
        marginTop: 10,
        fontWeight: '600',
        textAlign: 'right'
    }
});