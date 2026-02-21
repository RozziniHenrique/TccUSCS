import { useLocalSearchParams, useRouter } from 'expo-router';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';

export default function DetalhesFuncionario() {
    const { id } = useLocalSearchParams(); // Pega o ID da URL
    const router = useRouter();

    return (
        <View style={styles.container}>
            <TouchableOpacity onPress={() => router.back()} style={styles.backButton}>
                <Text style={styles.backText}>← Voltar</Text>
            </TouchableOpacity>

            <View style={styles.header}>
                <View style={styles.avatarPlaceholder}>
                    <Text style={styles.avatarText}>ID</Text>
                </View>
                <Text style={styles.title}>Perfil do Profissional</Text>
                <Text style={styles.subtitle}>Código de Identificação: {id}</Text>
            </View>

            <View style={styles.infoBox}>
                <Text style={styles.label}>Sobre</Text>
                <Text style={styles.description}>
                    Aqui você pode puxar mais dados do backend usando o ID {id} para mostrar a biografia,
                    fotos dos trabalhos e horários disponíveis.
                </Text>
            </View>

            <TouchableOpacity style={styles.appointmentButton}>
                <Text style={styles.appointmentText}>Agendar Horário</Text>
            </TouchableOpacity>
        </View>
    );
}

const styles = StyleSheet.create({
    container: { flex: 1, backgroundColor: '#fff', padding: 20, paddingTop: 60 },
    backButton: { marginBottom: 20 },
    backText: { color: '#3498db', fontSize: 16, fontWeight: 'bold' },
    header: { alignItems: 'center', marginBottom: 30 },
    avatarPlaceholder: { width: 80, height: 80, borderRadius: 40, backgroundColor: '#eee', justifyContent: 'center', alignItems: 'center', marginBottom: 10 },
    avatarText: { fontSize: 24, color: '#999' },
    title: { fontSize: 24, fontWeight: 'bold', color: '#2c3e50' },
    subtitle: { fontSize: 14, color: '#7f8c8d' },
    infoBox: { backgroundColor: '#f9f9f9', padding: 20, borderRadius: 12, marginBottom: 30 },
    label: { fontSize: 16, fontWeight: 'bold', color: '#333', marginBottom: 10 },
    description: { fontSize: 15, color: '#666', lineHeight: 22 },
    appointmentButton: { backgroundColor: '#2ecc71', padding: 18, borderRadius: 12, alignItems: 'center' },
    appointmentText: { color: '#fff', fontSize: 18, fontWeight: 'bold' }
});