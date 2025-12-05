const viewContactModal = document.getElementById('view_contact_model'); 

const baseURL = "http://localhost:8181";

const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModel(){
    contactModal.show();
}

function closeContactModel(){
    contactModal.hide();
}

async function loadContactData(id) {
    try {
        const response = await fetch(`${baseURL}/api/contacts/${id}`);
        const data = await response.json();

        // Safe setter function
        const set = (id, value) => {
            const el = document.querySelector(id);
            if (el) el.innerHTML = value || "";
        };

        set("#contact_name", data.name);
        set("#contact_email", data.email);
        set("#contact_address", data.address);
        set("#contact_about", data.description);

        // Image
        const img = document.querySelector("#contact_image");
        if (img) {
            img.src = data.picture || "your fallback image here";
        }

        // Phone
        set("#contact_phone", data.phoneNumber);

        // Favorite
        const favoriteEl = document.querySelector("#contact_favorite");
        if (favoriteEl) {
            favoriteEl.innerHTML = data.favorite
                ? "<i class='fas fa-star text-yellow-400'></i>".repeat(5)
                : "Not Favorite Contact";
        }

        // Links
        const website = document.querySelector("#contact_website");
        if (website) {
            website.href = data.webSiteLink;
            website.innerHTML = data.webSiteLink;
        }

        const linked = document.querySelector("#contact_linkedIn");
        if (linked) {
            linked.href = data.linkedInLink;
            linked.innerHTML = data.linkedInLink;
        }

        openContactModel();

    } catch (err) {
        console.error("Error loading contact:", err);
    }
}

// delete Contact

async function deleteContact(id) {
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
        cancelButtonText: "Cancel",
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        background: "#1f2937",       // gray-800
        color: "#fff",                // text white
    }).then((result) => {
        if (result.isConfirmed) {
            const url = `${baseURL}/user/contacts/delete/`+id;
            window.location.replace(url); 
            Swal.fire({
                title: "Deleted!",
                text: "Your contact has been removed.",
                icon: "success",
                background: "#1f2937",
                color: "#fff",
            });
        }
    });
}
