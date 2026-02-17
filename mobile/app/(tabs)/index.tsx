import { StyleSheet, Text, View } from 'react-native';

export default function HomeScreen() {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>STEFER - TccUSCS</Text>
      <Text style={styles.subtitle}>Trabalho de Conclusão de Curso -> Uscs</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
    backgroundColor: '#fff',
  },
  title: {
    fontSize: 28,
    fontWeight: 'bold',
    color: '#2b6cb0', // Um azul médico
  },
  subtitle: {
    fontSize: 16,
    color: '#666',
    marginTop: 10,
  },
});