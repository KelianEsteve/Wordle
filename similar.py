import random
from gensim.models import KeyedVectors
from flask import Flask, request, jsonify


def similar_word(mot_recherche):
    # Charger le modèle Word2Vec pré-entrainé en français
    model = KeyedVectors.load_word2vec_format("frWac_non_lem_no_postag_no_phrase_200_skip_cut100.bin", binary=True,
                                              unicode_errors="ignore")
    mots_similaires = model.most_similar(mot_recherche)
    # Mot pour lequel vous souhaitez trouver des mots similaires
    mot_recherche_lower = mot_recherche.lower()

    # Créer une liste de mots similaires qui ne contiennent pas le mot de recherche
    mots_similaires_filtres = [mot for mot, score in mots_similaires if mot_recherche_lower not in mot.lower()]

    # Sélectionner un mot au hasard parmi la liste filtrée
    mot_au_hasard = []
    mot_au_hasard.append(mots_similaires_filtres[0]);
    mot_au_hasard.append(mots_similaires_filtres[1]);
    mot_au_hasard.append(mots_similaires_filtres[2]);
    mot_au_hasard.append(mots_similaires_filtres[3]);

    return mot_au_hasard

app = Flask(__name__)

@app.route('/votre-endpoint', methods=['POST'])
def votre_endpoint():
    try:
        # Récupérez le JSON envoyé par le client Java
        data = request.json
        # Obtenez le mot aléatoire envoyé par le client Java
        mot_aleatoire = data.get("motAleatoire")
        # Utilisez le mot aléatoire dans votre fonction similar_word
        mot_choisi = similar_word(mot_aleatoire)
        # Vous pouvez renvoyer le mot choisi dans la réponse JSON
        response = {'motChoisi': mot_choisi}
        return jsonify(response), 200
    except Exception as e:
        return jsonify({'error': str(e)}), 500


if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8080)
